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
 * Registration reference holder for a Veto handling a MessageType on a MessageBus.
 * 
 * @author Paul Merlin 
 */
public final class VetoRegistration
{

    private final MessageBus msgBus;
    private final Veto veto;
    private final MessageType<?> type;

    /**
     * Build a Registration reference holder for a Veto handling a MessageType on a MessageBus.
     *
     * @param <S>       Registration's Subscriber
     * @param msgBus    Host MessageBus
     * @param type      Registration's MessageType
     * @param veto      Veto
     */
    /* package */ <S extends Subscriber> VetoRegistration( MessageBus msgBus, MessageType<S> type, Veto veto )
    {
        this.msgBus = msgBus;
        this.veto = veto;
        this.type = type;
    }

    /**
     * Unsubscribe from the host MessageBus.
     *
     * Unsubscription can be done on the MessageBus without reference to this VetoRegistration,
     * you'd better stick to one way per application layer to avoid spaghetti code.
     */
    @SuppressWarnings( "unchecked" )
    public void unregister()
    {
        msgBus.unregisterVeto( ( MessageType<Subscriber> ) type, veto );
    }

}
