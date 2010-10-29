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

public interface MessageBus
{

    /**
     * Publish given message to all instances of Subscriber.
     * @param <S>           Subscriber mark type, for type safety
     * @param message       Message, must not be null
     */
    <S extends Subscriber> void publish( Message<S> message );

    /**
     * Publish given message to all instances of Subscriber.
     * @param <S>           Subscriber mark type, for type safety
     * @param message       Message, must not be null
     * @param callback      Callback invoked after message delivery
     */
    <S extends Subscriber> void publish( Message<S> message, DeliveryCallback callback );

    /**
     * @param <S>           Subscriber mark type, for type safety
     * @param messageType   MessageType
     * @param subscriber    Subscriber
     * @return              Subscribtion
     */
    <S extends Subscriber> Subscribtion subscribe( MessageType<S> messageType, S subscriber );

    /**
     * Unregister Subscriber's Subscribtion for a MessageType.
     *
     * Unsubscription can be done by Subscription instances themselves too, you'd better stick
     * to one way per application layer to avoid spaghetti code.
     *
     * @param <S>           Subscriber mark type, for type safety
     * @param messageType   MessageType
     * @param subscriber    Subscriber
     */
    <S extends Subscriber> void unsubscribe( MessageType<S> messageType, S subscriber );

    /**
     * @param <S>           Subscriber mark type, for type safety
     * @param type          MessageType
     * @param veto          Veto
     * @return              VetoRegistration
     */
    <S extends Subscriber> VetoRegistration registerVeto( MessageType<S> type, Veto veto );

    /**
     * Unregister Veto's VetoRegistration for a MessageType.
     *
     * Unsubscription can be done by VetoRegistration instances themselves too, you'd better stick
     * to one way per application layer to avoid spaghetti code.
     *
     * @param <S>           Subscriber mark type, for type safety
     * @param type          MessageType
     * @param veto          Veto
     */
    <S extends Subscriber> void unregisterVeto( MessageType<S> type, Veto veto );

    /**
     * @param <S>           Subscriber mark type, for type safety
     * @param messageType   MessageType
     * @param id            Subscriber's ID
     * @return              Subscriber
     */
    <S extends Subscriber> S getSubscriber( MessageType<S> messageType, int id );

    /**
     * @param <S>           Subscriber mark type, for type safety
     * @param type          MessageType
     * @return              Whether the MessageBus has Subscriber instances for the given MessageType
     */
    <S extends Subscriber> boolean hasSubscribers( MessageType<S> type );

    /**
     * @param <S>           Subscriber mark type, for type safety
     * @param type          MessageType
     * @return              How many Subscriber instances the MessageBus has for the given MessageType
     */
    <S extends Subscriber> int countSubscribers( MessageType<S> type );

}
