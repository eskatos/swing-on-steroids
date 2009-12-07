package org.codeartisans.java.sos.messagebus;

/**
 * Protected contract (abstract class with no responsibility) of Message instances.
 * @param <S> MessageType's Subscriber mark
 */
public abstract class Message<S extends Subscriber>
{

    /**
     * @return MessageType
     */
    protected abstract MessageType<S> getMessageType();

    /**
     * @param subscriber Subscriber
     */
    protected abstract void deliver(S subscriber);
}
