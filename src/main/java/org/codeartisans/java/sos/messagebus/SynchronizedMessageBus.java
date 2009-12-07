package org.codeartisans.java.sos.messagebus;

import com.google.inject.Singleton;

@Singleton
public class SynchronizedMessageBus
        extends BaseMessageBus
{

    @Override
    public <S extends Subscriber> void publish(Message<S> message)
    {
        for (S eachSubscriber : get(message.getMessageType())) {
            message.deliver(eachSubscriber);
        }
    }
}
