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
 * Registration reference holder for a Veto handling a MessageType on a MessageBus.
 * 
 * @author Paul Merlin <paul@nosphere.org>
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
