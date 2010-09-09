/*
 * Copyright (c) 2009 Paul Merlin <paul@nosphere.org>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.codeartisans.java.sos.messagebus;

import java.util.concurrent.CountDownLatch;

import org.codeartisans.java.sos.SOSFailure;
import org.codeartisans.java.sos.threading.WorkQueueComposite;
import org.codeartisans.java.toolbox.ObjectHolder;

import org.qi4j.api.injection.scope.Service;
import org.qi4j.api.injection.scope.Structure;
import org.qi4j.api.unitofwork.UnitOfWork;
import org.qi4j.api.unitofwork.UnitOfWorkCompletionException;
import org.qi4j.api.unitofwork.UnitOfWorkFactory;

/**
 * MessageBus Mixin that use a thread to iterate over Subscribers and a thread per Message delivery.
 * The publish method returns immediatly.
 */
public abstract class MultiThreadDeliveryMixin
        extends BaseMessageBus
        implements MessageBusComposite
{

    @Structure
    private UnitOfWorkFactory uowf;
    @Service
    private WorkQueueComposite workQueue;

    @Override
    public <S extends Subscriber> void publish( final Message<S> message )
    {
        workQueue.enqueue( new Runnable()
        {

            @Override
            public void run()
            {
                if ( !vetoed( message ) ) {
                    for ( final S eachSubscriber : subscribers( message.getMessageType() ) ) {
                        workQueue.enqueue( new Runnable()
                        {

                            @Override
                            public void run()
                            {
                                final UnitOfWork uow = uowf.newUnitOfWork();
                                try {
                                    message.deliver( eachSubscriber );
                                    uow.complete();
                                } catch ( UnitOfWorkCompletionException ex ) {
                                    uow.discard();
                                    throw new SOSFailure( "Error during message delivery", ex );
                                }
                            }

                        } );
                    }
                }
            }

        } );
    }

    @Override
    public <S extends Subscriber> void publish( final Message<S> message, final DeliveryCallback callback )
    {
        workQueue.enqueue( new Runnable()
        {

            @Override
            public void run()
            {
                final ObjectHolder<Boolean> someSubscriberRefusedTheDelivery = new ObjectHolder<Boolean>( false );
                final CountDownLatch latch = new CountDownLatch( subscribers( message.getMessageType() ).size() );
                if ( !vetoed( message ) ) {
                    for ( final S eachSubscriber : subscribers( message.getMessageType() ) ) {
                        workQueue.enqueue( new Runnable()
                        {

                            @Override
                            public void run()
                            {
                                final UnitOfWork uow = uowf.newUnitOfWork();
                                try {
                                    try {
                                        message.deliver( eachSubscriber );
                                    } catch ( DeliveryRefusalException refusal ) {
                                        someSubscriberRefusedTheDelivery.setHolded( true );
                                    }
                                    uow.complete();
                                } catch ( UnitOfWorkCompletionException ex ) {
                                    uow.discard();
                                    throw new SOSFailure( "Error during message delivery", ex );
                                } finally {
                                    latch.countDown();
                                }
                            }

                        } );
                    }
                }
                try {
                    latch.await();
                } catch ( InterruptedException ignored ) {
                }
                if ( callback != null ) {
                    callback.afterDelivery( someSubscriberRefusedTheDelivery.getHolded() );
                }
            }

        } );
    }

}
