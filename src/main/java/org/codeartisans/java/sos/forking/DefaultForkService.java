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

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.codeartisans.java.toolbox.async.CallbackWithE;

public final class DefaultForkService
        implements ForkService
{

    private static final Logger LOGGER = LoggerFactory.getLogger( DefaultForkService.class );
    private CopyOnWriteArrayList<Process> processToKillOnShutdown;
    private boolean shutdownHookAdded = false;
    private final ThreadGroup threadGroup;

    public DefaultForkService()
    {
        threadGroup = new ThreadGroup( "ForkServiceThreads" );
    }

    @Override
    public void fork( Forkable forkable, ShutdownAction shutdownAction )
    {
        fork( forkable, null, shutdownAction );
    }

    @Override
    public void fork( Forkable forkable, final CallbackWithE<Void, ForkFault> exitCallback, final ShutdownAction shutdownAction )
    {
        try {
            final String forkUuid = UUID.randomUUID().toString();
            final ProcessBuilder builder = new ProcessBuilder( forkable.command() );
            builder.directory( forkable.workingDirectory() );
            final Process proc = builder.start();
            final StreamGobbler stderr = new StreamGobbler( proc.getErrorStream(), threadGroup, forkUuid + "-STDERR-Gobbler" );
            final StreamGobbler stdout = new StreamGobbler( proc.getInputStream(), threadGroup, forkUuid + "-STDOUT-Gobbler" );
            stderr.setDaemon( true );
            stdout.setDaemon( true );
            stderr.start();
            stdout.start();
            final Thread watcher = new Thread( threadGroup, forkUuid + "-Watcher" )
            {

                @Override
                public void run()
                {
                    try {
                        beforeProcessWaitFor();
                        proc.waitFor();
                        afterProcessWaitFor();
                        callback();
                        interrupt();
                    } catch ( InterruptedException iex ) {
                        ForkFault ex = new ForkFault( "Fork Watcher " + forkUuid + "interrupted!", iex );
                        if ( exitCallback != null ) {
                            exitCallback.onError( ex.getMessage(), ex );
                        } else {
                            LOGGER.error( ex.getMessage(), ex );
                        }
                    }
                }

                private void beforeProcessWaitFor()
                {
                    if ( shutdownAction == ShutdownAction.KILL ) {
                        ensureShutdownHook();
                        addKillOnShutdownProcess( proc );
                    }
                }

                private void afterProcessWaitFor()
                {
                    stderr.interrupt();
                    stdout.interrupt();
                    if ( shutdownAction == ShutdownAction.KILL ) {
                        removeKillOnShutdownProcess( proc );
                    }
                }

                private void callback()
                {
                    int status = proc.exitValue();
                    if ( status == 0 ) {
                        if ( exitCallback != null ) {
                            exitCallback.onSuccess( null );
                        }
                    } else {
                        if ( exitCallback != null ) {
                            exitCallback.onError( "Fork " + forkUuid + " exited with error, status was: " + status, null );
                        }
                    }
                }

            };
            watcher.setDaemon( true );
            watcher.start();
        } catch ( IOException ex ) {
            throw new ForkFault( "Unable to fork: " + ex.getMessage(), ex );
        }
    }

    private synchronized void ensureShutdownHook()
    {
        if ( !shutdownHookAdded ) {
            Runtime.getRuntime().addShutdownHook( new Thread( threadGroup, new Runnable()
            {

                @Override
                public void run()
                {
                    if ( processToKillOnShutdown != null ) {
                        for ( Process eachProcessToKill : processToKillOnShutdown ) {
                            eachProcessToKill.destroy();
                            LOGGER.debug( "Forked process destroyed: " + eachProcessToKill.toString() );
                        }
                    }
                }

            }, getClass().getSimpleName() + ".ShutdownHook" ) );
            shutdownHookAdded = true;
        }
    }

    private void addKillOnShutdownProcess( Process proc )
    {
        if ( processToKillOnShutdown == null ) {
            processToKillOnShutdown = new CopyOnWriteArrayList<Process>();
        }
        processToKillOnShutdown.add( proc );
    }

    private void removeKillOnShutdownProcess( Process proc )
    {
        if ( processToKillOnShutdown != null ) {
            processToKillOnShutdown.remove( proc );
        }
    }

}
