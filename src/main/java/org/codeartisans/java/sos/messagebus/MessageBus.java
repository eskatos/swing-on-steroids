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

public interface MessageBus
{

    /**
     * Publish given message to all instances of Subscriber.
     * @param <S>           Subscriber mark type, for type safety
     * @param message       Message, must not be null
     */
    <S extends Subscriber> void publish( Message<S> message );

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
