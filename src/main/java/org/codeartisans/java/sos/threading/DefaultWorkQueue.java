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
package org.codeartisans.java.sos.threading;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import java.util.LinkedList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.codeartisans.java.toolbox.exceptions.NullArgumentException;

public final class DefaultWorkQueue
        implements WorkQueue
{

    private static final Logger LOGGER = LoggerFactory.getLogger( DefaultWorkQueue.class );
    // CHECKSTYLE:OFF We use a specific List implementation on purpose
    private final LinkedList<Runnable> queue;
    // CHECKSTYLE:ON

    @Inject
    public DefaultWorkQueue( @Named( NAME ) String name, @Named( SIZE ) Integer size )
    {
        NullArgumentException.ensureNotEmpty( NAME, true, name );
        NullArgumentException.ensureNotZero( SIZE, size );
        queue = new LinkedList<Runnable>();
        PooledWorker[] threads = new PooledWorker[ size ];
        ThreadGroup threadGroup = new ThreadGroup( name + "WorkQueue" );
        for ( int i = 0; i < size; i++ ) {
            threads[i] = new PooledWorker( threadGroup, name + "Worker-" + i );
            threads[i].start();
        }
    }

    @Override
    public void enqueue( Runnable r )
    {
        synchronized ( queue ) {
            queue.addLast( r );
            queue.notifyAll();
        }
    }

    private final class PooledWorker
            extends Thread
    {

        private PooledWorker( ThreadGroup threadGroup, String threadName )
        {
            super( threadGroup, threadName );
        }

        @Override
        public void run()
        {
            Runnable r;
            while ( true ) {
                synchronized ( queue ) {
                    while ( queue.isEmpty() ) {
                        try {
                            queue.wait();
                        } catch ( InterruptedException ignored ) {
                        }
                    }
                    r = queue.removeFirst();
                }
                // If we don't catch RuntimeException, the pool could leak threads
                try {
                    r.run();
                } catch ( RuntimeException ex ) {
                    LOGGER.warn( ex.getMessage(), ex );
                }
            }
        }

    }

}
