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

import org.junit.Assert;

/**
 * @author Paul Merlin <paul@nosphere.org>
 */
public interface Prout
{

    static class Util
    {

        static void testMessageBus(MessageBus msgBus) throws InterruptedException
        {
            TestProutMessageHandler sub1 = new TestProutMessageHandler();
            TestProutMessageHandler sub2 = new TestProutMessageHandler();

            Subscribtion s1 = msgBus.subscribe(ProutMessage.TYPE, sub1);
            Assert.assertEquals(1, msgBus.countSubscribers(ProutMessage.TYPE));

            msgBus.publish(new ProutMessage());
            Thread.sleep(100);

            Subscribtion s2 = msgBus.subscribe(ProutMessage.TYPE, sub2);
            Assert.assertEquals(2, msgBus.countSubscribers(ProutMessage.TYPE));

            msgBus.publish(new ProutMessage());
            Thread.sleep(100);

            Assert.assertEquals(2, sub1.calls);
            Assert.assertEquals(1, sub2.calls);

            s1.unsubscribe();
            Assert.assertEquals(1, msgBus.countSubscribers(ProutMessage.TYPE));
            s2.unsubscribe();
            Assert.assertEquals(0, msgBus.countSubscribers(ProutMessage.TYPE));

        }

    }

    static class ProutMessage
            extends Message<ProutMessageHandler>
    {

        public static final MessageType<ProutMessageHandler> TYPE = new MessageType<ProutMessageHandler>();

        @Override
        public MessageType<ProutMessageHandler> getMessageType()
        {
            return TYPE;
        }

        @Override
        protected void deliver(ProutMessageHandler handler)
        {
            handler.onProut(this);
        }

    }

    interface ProutMessageHandler
            extends Subscriber
    {

        void onProut(ProutMessage prout);

    }

    class TestProutMessageHandler
            implements ProutMessageHandler
    {

        int calls = 0;

        @Override
        public void onProut(ProutMessage prout)
        {
            calls++;
        }

    }

}
