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
package org.codeartisans.java.sos.forking;

import org.codeartisans.java.toolbox.async.CallbackWithE;

public interface ForkService
{

    public enum ShutdownAction
    {

        KILL, LET_LIVE
    }

    /**
     * Run a Forkable as a background process with a given ShutdownAction.
     * 
     * @param forkable          External process command definition
     * @param shutdownAction    What should the ForkService do when the JVM shuts down
     */
    void fork( Forkable forkable, ShutdownAction shutdownAction );

    /**
     * Run a Forkable as a background process with given callback and ShutdownAction.
     *
     * @param forkable          External process command definition
     * @param exitCallback      A callback triggered when the external process exit
     * @param shutdownAction    What should the ForkService do when the JVM shuts down
     */
    void fork( Forkable forkable, CallbackWithE<Void, ForkFault> exitCallback, ShutdownAction shutdownAction );

}
