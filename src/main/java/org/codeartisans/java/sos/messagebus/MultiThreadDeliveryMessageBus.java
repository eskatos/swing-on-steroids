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

import com.google.inject.Inject;


import org.codeartisans.java.sos.threading.WorkQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * MessageBus Implementation that use a thread to iterate over Subscribers and a thread per Message delivery.
 * The publish method returns immediatly.
 */
public final class MultiThreadDeliveryMessageBus
        extends BaseMessageBus
{
    private static final Logger LOGGER = LoggerFactory.getLogger( MultiThreadDeliveryMessageBus.class );

    private final WorkQueue workQueue;

    @Inject
    public MultiThreadDeliveryMessageBus( WorkQueue workQueue )
    {
        this.workQueue = workQueue;
    }

    @Override
    public <S extends Subscriber> void publish( final Message<S> message )
    {
        workQueue.enqueue( new Runnable()
        {

            @Override
            public void run()
            {
                if ( !vetoed( message ) ) {
                    if(LOGGER.isTraceEnabled()) {
                        LOGGER.trace( "Publishing message {}", message.getClass().getSimpleName() );
                    }
                    for ( final S eachSubscriber : subscribers( message.getMessageType() ) ) {
                        workQueue.enqueue( new Runnable()
                        {

                            @Override
                            public void run()
                            {
                                try {
                                    message.deliver( eachSubscriber );
                                } catch ( DeliveryRefusalException refusal ) {
                                }
                            }

                        } );
                    }
                    if(LOGGER.isTraceEnabled()) {
                        LOGGER.trace( "Message published {}", message.getClass().getSimpleName() );
                    }
                }
            }

        } );
    }

}
