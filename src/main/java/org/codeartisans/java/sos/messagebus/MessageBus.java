package org.codeartisans.java.sos.messagebus;

public interface MessageBus
{

    /**
     * @param <S>           Subscriber mark type, for type safety
     * @param messageType   MessageType
     * @param subscriber    Subscriber
     * @return              Subscribtion
     */
    <S extends Subscriber> Subscribtion subscribe(MessageType<S> messageType, S subscriber);

    /**
     * Unregister Subscriber's Subscribtion for a MessageType.
     *
     * Unsubscription can be done by Subscription instances themselves too, you'd better stick
     * to one way per application layer to avoid spaghetti code.
     *
     * @param <S>           Subscriber mark type, for type safety
     * @param messageType   MessageType
     * @param subscriber    Subscriber
     */
    <S extends Subscriber> void unsubscribe(MessageType<S> messageType, S subscriber);

    /**
     * Publish given message to all instances of Subscriber.
     * @param <S>           Subscriber mark type, for type safety
     * @param message       Message
     */
    <S extends Subscriber> void publish(Message<S> message);

    /**
     * @param <S>           Subscriber mark type, for type safety
     * @param messageType   MessageType
     * @param id            Subscriber's ID
     * @return              Subscriber
     */
    <S extends Subscriber> S getSubscriber(MessageType<S> messageType, int id);

    /**
     * @param <S>           Subscriber mark type, for type safety
     * @param messageType   MessageType
     * @return              Whether the MessageBus has Subscriber instances for the given MessageType
     */
    <S extends Subscriber> boolean hasSubscribers(MessageType<S> messageType);

    /**
     * @param <S>           Subscriber mark type, for type safety
     * @param messageType   MessageType
     * @return              How many Subscriber instances the MessageBus has for the given MessageType
     */
    <S extends Subscriber> int countSubscribers(MessageType<S> messageType);

}
