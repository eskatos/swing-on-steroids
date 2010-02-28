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

import org.codeartisans.java.sos.threading.WorkQueueComposite;
import org.qi4j.api.injection.scope.Service;
import org.qi4j.api.injection.scope.Structure;
import org.qi4j.api.unitofwork.UnitOfWork;
import org.qi4j.api.unitofwork.UnitOfWorkCompletionException;
import org.qi4j.api.unitofwork.UnitOfWorkFactory;

public abstract class SingleThreadDeliveryMixin
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

                final UnitOfWork uow = uowf.newUnitOfWork();
                try {
                    for ( S eachSubscriber : get( message.getMessageType() ) ) {
                        message.deliver( eachSubscriber );
                    }
                    uow.complete();
                } catch ( UnitOfWorkCompletionException ex ) {
                    ex.printStackTrace();
                    uow.discard();
                    throw new InternalError( "Error during: " + ex.getMessage() );
                }
            }
        } );
    }
}
