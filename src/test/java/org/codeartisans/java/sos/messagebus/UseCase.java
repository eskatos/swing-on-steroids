/*
 * Copyright (c) 2009, Paul Merlin. All Rights Reserved.
 * Copyright (c) 2010, Fabien Barbero. All Rights Reserved.
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

import org.codeartisans.java.toolbox.ObjectHolder;
import org.junit.Assert;

/**
 * @author Paul Merlin
 */
@SuppressWarnings( "PublicInnerClass" )
public interface UseCase
{

    static class Util
    {

        static void testMessageBus( MessageBus msgBus )
                throws InterruptedException
        {
            TestMessageHandlerImpl sub1 = new TestMessageHandlerImpl();
            TestMessageHandlerImpl sub2 = new TestMessageHandlerImpl();

            Subscribtion s1 = msgBus.subscribe( TestMessage.TYPE, sub1 );
            Assert.assertEquals( 1, msgBus.countSubscribers( TestMessage.TYPE ) );

            msgBus.publish( new TestMessage() );
            Thread.sleep( 100 );

            Subscribtion s2 = msgBus.subscribe( TestMessage.TYPE, sub2 );
            Assert.assertEquals( 2, msgBus.countSubscribers( TestMessage.TYPE ) );

            msgBus.publish( new TestMessage() );
            Thread.sleep( 100 );

            Assert.assertEquals( 2, sub1.calls );
            Assert.assertEquals( 1, sub2.calls );

            s1.unsubscribe();
            Assert.assertEquals( 1, msgBus.countSubscribers( TestMessage.TYPE ) );

            VetoRegistration vetoRegistration = msgBus.registerVeto( TestMessage.TYPE, new VetoAlways() );

            msgBus.publish( new TestMessage() );
            msgBus.publish( new TestMessage() );
            msgBus.publish( new TestMessage() );
            msgBus.publish( new TestMessage() );
            Thread.sleep( 100 );

            Assert.assertEquals( 1, sub2.calls );

            vetoRegistration.unregister();

            vetoRegistration = msgBus.registerVeto( TestMessage.TYPE, new VetoOnce() );

            msgBus.publish( new TestMessage() );
            msgBus.publish( new TestMessage() );
            Thread.sleep( 100 );

            Assert.assertEquals( 2, sub2.calls );

            s2.unsubscribe();
            Assert.assertEquals( 0, msgBus.countSubscribers( TestMessage.TYPE ) );


            // Test refusals
            msgBus.subscribe( TestMessage.TYPE, new TestRefuserMessageHandlerImpl() );
            msgBus.publish( new TestMessage() );
        }

    }

    static class TestMessage
            extends Message<TestMessageHandler>
    {

        public static final MessageType<TestMessageHandler> TYPE = new MessageType<TestMessageHandler>();

        @Override
        public MessageType<TestMessageHandler> getMessageType()
        {
            return TYPE;
        }

        @Override
        protected void deliver( TestMessageHandler handler )
        {
            handler.onProut( this );
        }

    }

    interface TestMessageHandler
            extends Subscriber
    {

        void onProut( TestMessage prout );

    }

    @SuppressWarnings( "PackageVisibleField" )
    class TestMessageHandlerImpl
            implements TestMessageHandler
    {

        int calls = 0;

        @Override
        public void onProut( TestMessage prout )
        {
            calls++;
        }

    }

    class TestRefuserMessageHandlerImpl
            implements TestMessageHandler
    {

        @Override
        public void onProut( TestMessage prout )
        {
            throw new DeliveryRefusalException();
        }

    }

}
