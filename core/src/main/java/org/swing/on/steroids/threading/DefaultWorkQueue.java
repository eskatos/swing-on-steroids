/*
 * Copyright (c) 2009, Paul Merlin. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package org.swing.on.steroids.threading;

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
