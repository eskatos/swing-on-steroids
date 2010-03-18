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

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Base MessageBus implementation.
 *
 * Only the publish method is abstract, other ones are final.
 *
 * Safe but could be too slow or too fat for your usage.  New implementations will be done/accepted
 * with a convincing test case to keep MessageBus small.
 */
public abstract class BaseMessageBus
        implements MessageBus
{

    private final ConcurrentHashMap<MessageType<?>, CopyOnWriteArrayList<?>> subscribers = new ConcurrentHashMap<MessageType<?>, CopyOnWriteArrayList<?>>();
    private final ConcurrentHashMap<MessageType<?>, CopyOnWriteArrayList<?>> vetos = new ConcurrentHashMap<MessageType<?>, CopyOnWriteArrayList<?>>();

    @Override
    public abstract <S extends Subscriber> void publish( Message<S> msg );

    @Override
    public <S extends Subscriber> Subscribtion subscribe( MessageType<S> type, S subscriber )
    {
        subscribers( type ).add( subscriber );
        return new Subscribtion( this, type, subscriber );
    }

    @Override
    public <S extends Subscriber> void unsubscribe( MessageType<S> type, S subscriber )
    {
        CopyOnWriteArrayList<S> l = subscribers( type );
        boolean result = l.remove( subscriber );
        if ( l.size() == 0 ) {
            subscribers.remove( type );
        }
        assert result : "Tried to remove unknown subscriber: " + subscriber + " for " + type;
    }

    @Override
    public <S extends Subscriber> VetoRegistration registerVeto( MessageType<S> type, Veto veto )
    {
        vetos( type ).add( veto );
        return new VetoRegistration( this, type, veto );
    }

    @Override
    public <S extends Subscriber> void unregisterVeto( MessageType<S> type, Veto veto )
    {
        CopyOnWriteArrayList<Veto> l = vetos( type );
        boolean result = l.remove( veto );
        if ( l.size() == 0 ) {
            vetos.remove( type );
        }
        assert result : "Tried to remove unknown veto: " + veto + " for " + type;
    }

    @Override
    public <S extends Subscriber> S getSubscriber( MessageType<S> type, int index )
    {
        return subscribers( type ).get( index );
    }

    @Override
    public <S extends Subscriber> boolean hasSubscribers( MessageType<S> type )
    {
        return subscribers.containsKey( type );
    }

    @Override
    public <S extends Subscriber> int countSubscribers( MessageType<S> type )
    {
        CopyOnWriteArrayList<?> l = subscribers.get( type );
        return l == null ? 0 : l.size();
    }

    /**
     * @param <S>           Subscriber mark type, for type safety
     * @param message       Message
     * @return              True if the Message is vetoed, false otherwise
     */
    protected final <S extends Subscriber> boolean vetoed( Message<S> message )
    {
        CopyOnWriteArrayList<Veto> msgVetos = vetos( message.getMessageType() );
        for ( Veto eachVeto : msgVetos ) {
            if ( eachVeto.veto( message ) ) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param <S>           Subscriber mark type, for type safety
     * @param type          MessageType
     * @return              The Subscriber collection for a MessageType
     */
    @SuppressWarnings( "unchecked" )
    protected final <S extends Subscriber> CopyOnWriteArrayList<S> subscribers( MessageType<S> type )
    {
        subscribers.putIfAbsent( type, new CopyOnWriteArrayList<S>() );
        // This cast is safe because we control the puts.
        return ( CopyOnWriteArrayList<S> ) subscribers.get( type );
    }

    /**
     * @param <S>           Subscriber mark type, for type safety
     * @param type          MessageType
     * @return              The Veto collection for a MessageType
     */
    @SuppressWarnings( "unchecked" )
    protected final <S extends Subscriber> CopyOnWriteArrayList<Veto> vetos( MessageType<S> type )
    {
        vetos.putIfAbsent( type, new CopyOnWriteArrayList<Veto>() );
        // This cast is safe because we control the puts.
        return ( CopyOnWriteArrayList<Veto> ) vetos.get( type );
    }

}
