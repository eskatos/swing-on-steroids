package org.codeartisans.java.sos.messagebus;

import com.google.inject.Inject;
import org.codeartisans.java.sos.threading.WorkQueue;

public class MultiThreadDeliveryMessageBus
        extends BaseMessageBus
{

    private final WorkQueue workQueue;

    @Inject
    public MultiThreadDeliveryMessageBus(WorkQueue workQueue)
    {
        this.workQueue = workQueue;
    }

    @Override
    public <S extends Subscriber> void publish(final Message<S> message)
    {
        workQueue.execute(new Runnable()
        {

            @Override
            public void run()
            {
                for (final S eachSubscriber : get(message.getMessageType())) {
                    workQueue.execute(new Runnable()
                    {

                        @Override
                        public void run()
                        {
                            message.deliver(eachSubscriber);
                        }

                    });
                }
            }

        });
    }

}
