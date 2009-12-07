package org.codeartisans.java.sos.messagebus;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.codeartisans.java.sos.threading.WorkQueue;

@Singleton
public class SingleThreadDeliveryMessageBus
        extends BaseMessageBus
{

    private final WorkQueue workQueue;

    @Inject
    public SingleThreadDeliveryMessageBus(WorkQueue workQueue)
    {
        this.workQueue = workQueue;
    }

    public <S extends Subscriber> void publish(final Message<S> message)
    {
        workQueue.execute(new Runnable()
        {

            public void run()
            {
                for (S eachSubscriber : get(message.getMessageType())) {
                    message.deliver(eachSubscriber);
                }
            }
        });
    }
}
