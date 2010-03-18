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
