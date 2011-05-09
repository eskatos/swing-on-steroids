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
package org.swing.on.steroids.messagebus;

/**
 * Subscribtion reference holder for a MessageType on a MessageBus.
 */
public final class Subscribtion
{

    private final MessageBus messageBus;
    private final Subscriber subscriber;
    private final MessageType<?> messageType;

    /**
     * Build a Subscribtion reference holder for a MessageType on a MessageBus.
     *
     * @param <S>           Subscribtion's Subscriber
     * @param messageBus    Host MessageBus
     * @param messageType   Subscribtion's MessageType
     * @param subscriber    Subscriber
     */
    /* package */ <S extends Subscriber> Subscribtion( MessageBus messageBus, MessageType<S> messageType, S subscriber )
    {
        this.messageBus = messageBus;
        this.subscriber = subscriber;
        this.messageType = messageType;
    }

    /**
     * Unsubscribe from the host MessageBus.
     *
     * Unsubscription can be done on the MessageBus without reference to this Subscribtion,
     * you'd better stick to one way per application layer to avoid spaghetti code.
     */
    @SuppressWarnings( "unchecked" )
    public void unsubscribe()
    {
        messageBus.unsubscribe( ( MessageType<Subscriber> ) messageType, subscriber );
    }

}
