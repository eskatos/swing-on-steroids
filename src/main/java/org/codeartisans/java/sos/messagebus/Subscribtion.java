package org.codeartisans.java.sos.messagebus;

/**
 * Subscribtion reference holder for a MessageType on a MessageBus.
 */
public class Subscribtion
{

    private final MessageBus messageBus;
    private final Subscriber subscriber;
    private final MessageType<?> messageType;

    /**
     * Build a Subscribtion reference holder for a MessageType on a MessageBus.
     *
     * @param <S>           Subscribtion's Subscriber
     * @param messageBus    Host MessageBus
     * @param messageType   Subscribtion's MessageType
     * @param subscriber    Subscriber
     */
    protected <S extends Subscriber> Subscribtion(MessageBus messageBus, MessageType<S> messageType, S subscriber)
    {
        this.messageBus = messageBus;
        this.subscriber = subscriber;
        this.messageType = messageType;
    }

    /**
     * Unsubscribe from the host MessageBus.
     *
     * Unsubscription can be done on the MessageBus without reference to this Subscribtion,
     * you'd better stick to one way per application layer to avoid spaghetti code.
     */
    @SuppressWarnings("unchecked")
    public void unsubscribe()
    {
        messageBus.unsubscribe((MessageType<Subscriber>) messageType, subscriber);
    }
}
