package org.codeartisans.java.sos.messagebus;

/**
 * Pivot of the MessageBus type safety.
 *
 * Each instance is backed by an integer used as its hashCode. A static integer, starting at 0
 * is incremented for each instance. This ties the MessageBus implementations into one jvm only.
 *
 * Bridging different buses can be done with one subscriber on each with care about failures.
 * TODO integration layer ? .. voir dans DDD comment c'est nommé
 *
 * @param <S> Subscriber mark
 */
public class MessageType<S extends Subscriber>
{

    private static int nextHashCode;
    private final int index;

    public MessageType()
    {
        super();
        index = ++nextHashCode;
    }

    @Override
    public final int hashCode()
    {
        // We override hash code to make it as efficient as possible.
        return index;
    }
}
