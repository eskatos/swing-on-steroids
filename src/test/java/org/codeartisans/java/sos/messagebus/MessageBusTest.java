package org.codeartisans.java.sos.messagebus;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Singleton;
import com.google.inject.name.Names;
import org.codeartisans.java.sos.threading.DefaultWorkQueue;
import org.codeartisans.java.sos.threading.WorkQueue;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MessageBusTest
{

    private class MessageBusTestModule extends AbstractModule
    {

        @Override
        protected void configure()
        {
            bind(String.class).annotatedWith(Names.named(WorkQueue.NAME)).toInstance("MessageBusTest");
            bind(Integer.class).annotatedWith(Names.named(WorkQueue.SIZE)).toInstance(2);
            bind(WorkQueue.class).to(DefaultWorkQueue.class).in(Singleton.class);
            bind(MessageBus.class).to(SingleThreadDeliveryMessageBus.class);
        }

    }

    private MessageBus msgBus;

    @Before
    public void setUp()
    {
        msgBus = Guice.createInjector(new MessageBusTestModule()).getInstance(MessageBus.class);

    }

    @After
    public void tearDown()
    {
        msgBus = null;
    }

    @Test
    public void test() throws InterruptedException
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

        private int calls = 0;

        @Override
        public void onProut(ProutMessage prout)
        {
            calls++;
        }

    }

}
