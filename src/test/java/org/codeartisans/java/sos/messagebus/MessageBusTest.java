package org.codeartisans.java.sos.messagebus;

import org.codeartisans.java.sos.messagebus.MessageBus;
import org.codeartisans.java.sos.messagebus.MessageType;
import org.codeartisans.java.sos.messagebus.Subscriber;
import org.codeartisans.java.sos.messagebus.SingleThreadDeliveryMessageBus;
import org.codeartisans.java.sos.messagebus.Subscribtion;
import org.codeartisans.java.sos.messagebus.Message;
import atunit.AtUnit;
import atunit.Container;
import atunit.Unit;
import atunit.guice.GuiceContainer;
import com.google.inject.Binder;
import com.google.inject.Inject;
import com.google.inject.Module;
import com.google.inject.Singleton;
import com.google.inject.name.Names;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.codeartisans.java.sos.threading.WorkQueue;
import org.codeartisans.java.sos.threading.DefaultWorkQueue;

@RunWith(AtUnit.class)
@Container(GuiceContainer.class)
public class MessageBusTest
        implements Module
{

    @Override
    public void configure(Binder binder)
    {
        binder.bind(String.class).annotatedWith(Names.named(WorkQueue.NAME)).toInstance("BusTest");
        binder.bind(Integer.class).annotatedWith(Names.named(WorkQueue.SIZE)).toInstance(2);
        binder.bind(WorkQueue.class).to(DefaultWorkQueue.class).in(Singleton.class);
        binder.bind(MessageBus.class).to(SingleThreadDeliveryMessageBus.class);
    }

    @Inject
    @Unit
    private MessageBus msgBus;

    @Test
    public void prout()
            throws InterruptedException
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
