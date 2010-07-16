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

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.LinkedList;

import org.codeartisans.java.toolbox.async.ErrorCallbackAdapter;
import org.codeartisans.java.toolbox.exceptions.NullArgumentException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Paul Merlin
 * @author Fabien Barbero
 */
public final class DefaultWorkQueue
        implements WorkQueue
{

    private static final Logger LOGGER = LoggerFactory.getLogger( DefaultWorkQueue.class );
    // CHECKSTYLE:OFF We use a specific List implementation on purpose
    private final LinkedList<RunnableCallbackHolder> queue;
    // CHECKSTYLE:ON

    @Inject
    @SuppressWarnings( "CallToThreadStartDuringObjectConstruction" )
    public DefaultWorkQueue( @Named( NAME ) String name, @Named( SIZE ) Integer size )
    {
        NullArgumentException.ensureNotEmpty( NAME, true, name );
        NullArgumentException.ensureNotZero( SIZE, size );
        queue = new LinkedList<RunnableCallbackHolder>();
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
        enqueue( r, null );
    }

    @Override
    public void enqueue( Runnable runnable, ErrorCallbackAdapter<RuntimeException> errorCallback )
    {
        synchronized ( queue ) {
            queue.addLast( new RunnableCallbackHolder( runnable, errorCallback ) );
            queue.notifyAll();
        }
    }

    private class PooledWorker
            extends Thread
    {

        private PooledWorker( ThreadGroup threadGroup, String threadName )
        {
            super( threadGroup, threadName );
        }

        @Override
        public void run()
        {
            RunnableCallbackHolder holder;
            while ( true ) {
                synchronized ( queue ) {
                    while ( queue.isEmpty() ) {
                        try {
                            queue.wait();
                        } catch ( InterruptedException ignored ) {
                        }
                    }
                    holder = queue.removeFirst();
                }
                // If we don't catch RuntimeException, the pool could leak threads
                try {
                    holder.runnable().run();
                } catch ( RuntimeException ex ) {
                    if ( holder.errorCallback() != null ) {
                        holder.errorCallback().onError( ex.getMessage(), ex );
                    } else {
                        notifyUncaughtException( ex );
                    }
                }
            }
        }

        private void notifyUncaughtException( Throwable e )
        {
            UncaughtExceptionHandler handler = Thread.getDefaultUncaughtExceptionHandler();
            if ( handler != null ) {
                handler.uncaughtException( this, e );
            } else {
                LOGGER.error( "Uncaught exception in queued work - " + e.getMessage(), e );
            }
        }

    }

}
