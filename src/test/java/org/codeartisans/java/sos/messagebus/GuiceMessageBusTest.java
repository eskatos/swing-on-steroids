package org.codeartisans.java.sos.messagebus;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Singleton;
import com.google.inject.name.Names;
import org.codeartisans.java.sos.threading.DefaultWorkQueue;
import org.codeartisans.java.sos.threading.WorkQueue;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GuiceMessageBusTest
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
        UseCase.Util.testMessageBus(msgBus);
    }

}
