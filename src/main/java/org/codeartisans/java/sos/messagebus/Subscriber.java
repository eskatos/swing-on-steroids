package org.codeartisans.java.sos.messagebus;

/**
 * Mark type for all Subscriber types.
 *
 * Implementations can be a Subscriber for several MessageType on several instances of MessageBus.
 *
 * This type is needed to keep MessageBus type safe.
 */
public interface Subscriber
{
}
