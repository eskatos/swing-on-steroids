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
        if ( l.isEmpty() ) {
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
        if ( l.isEmpty() ) {
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
