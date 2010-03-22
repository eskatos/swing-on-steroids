/*
 * Copyright (c) 2009 Paul Merlin <paul@nosphere.org>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.codeartisans.java.sos.messagebus;

/**
 * Pivot of the MessageBus type safety.
 *
 * Each instance is backed by an integer used as its hashCode. A static integer, starting at 0
 * is incremented for each instance. This ties the MessageBus implementations into one jvm only.
 *
 * Bridging different buses can be done with one subscriber on each with care about failures.
 * TODO AntiCorruptionLayer
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

    // CHECKSTYLE:OFF equals is not implemented on purpose !
    @Override
    public final int hashCode()
    {
        // We override hash code to make it as efficient as possible
        return index;
    }
    // CHECKSTYLE:ON

}
