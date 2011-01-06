/*
 * Copyright (c) 2009, Paul Merlin. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package org.codeartisans.java.sos.messagebus;


import org.codeartisans.java.sos.SOSFailure;
import org.codeartisans.java.sos.threading.WorkQueueComposite;

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

}
