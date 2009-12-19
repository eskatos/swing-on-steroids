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

    private final ConcurrentHashMap<MessageType<?>, CopyOnWriteArrayList<?>> registry = new ConcurrentHashMap<MessageType<?>, CopyOnWriteArrayList<?>>();

    @Override
    public abstract <H extends Subscriber> void publish(Message<H> msg);

    @Override
    public <H extends Subscriber> Subscribtion subscribe(MessageType<H> type, H handler)
    {
        get(type).add(handler);
        return new Subscribtion(this, type, handler);
    }

    @Override
    public <H extends Subscriber> H getSubscriber(MessageType<H> type, int index)
    {
        return get(type).get(index);
    }

    @Override
    public <H extends Subscriber> int countSubscribers(MessageType<H> type)
    {
        CopyOnWriteArrayList<?> l = registry.get(type);
        return l == null ? 0 : l.size();
    }

    @Override
    public <H extends Subscriber> boolean hasSubscribers(MessageType<H> type)
    {
        return registry.containsKey(type);
    }

    @Override
    public <H extends Subscriber> void unsubscribe(MessageType<H> type, H handler)
    {
        CopyOnWriteArrayList<H> l = get(type);
        boolean result = l.remove(handler);
        if (l.size() == 0) {
            registry.remove(type);
        }
        assert result : "Tried to remove unknown handler: " + handler + " from " + type;
    }

    @SuppressWarnings("unchecked")
    protected <H extends Subscriber> CopyOnWriteArrayList<H> get(MessageType<H> type)
    {
        registry.putIfAbsent(type, new CopyOnWriteArrayList<H>());
        // This cast is safe because we control the puts.
        return (CopyOnWriteArrayList<H>) registry.get(type);
    }

}
