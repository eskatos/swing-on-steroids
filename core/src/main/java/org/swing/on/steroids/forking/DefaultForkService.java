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
package org.swing.on.steroids.forking;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.codeartisans.java.toolbox.async.CallbackWithE;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
        fork( forkable, null, shutdownAction, null, null );
    }

    @Override
    public void fork( Forkable forkable, ShutdownAction shutdownAction, OutputStream output )
    {
        fork( forkable, null, shutdownAction, output, output );
    }

    @Override
    public void fork( Forkable forkable, ShutdownAction shutdownAction, OutputStream stdOut, OutputStream errOut )
    {
        fork( forkable, null, shutdownAction, stdOut, errOut );
    }

    @Override
    public void fork( Forkable forkable, CallbackWithE<Void, ForkFault> exitCallback, ShutdownAction shutdownAction )
    {
        fork( forkable, exitCallback, shutdownAction, null, null );
    }

    @Override
    public void fork( Forkable forkable, CallbackWithE<Void, ForkFault> exitCallback, ShutdownAction shutdownAction, OutputStream output )
    {
        fork( forkable, exitCallback, shutdownAction, output, output );
    }

    @Override
    public void fork( final Forkable forkable, final CallbackWithE<Void, ForkFault> exitCallback, final ShutdownAction shutdownAction, OutputStream stdOut, OutputStream errOut )
    {
        try {
            final ProcessBuilder builder = new ProcessBuilder( forkable.command() );
            builder.directory( forkable.workingDirectory() );
            final Map<String, String> environment = forkable.environment();
            if ( environment != null ) {
                builder.environment().putAll( environment );
            }
            final Process proc = builder.start();
            final StreamGobbler stderr = new StreamGobbler( proc.getErrorStream(), threadGroup, forkable.name() + "-STDERR", errOut );
            final StreamGobbler stdout = new StreamGobbler( proc.getInputStream(), threadGroup, forkable.name() + "-STDOUT", stdOut );
            stderr.setDaemon( true );
            stdout.setDaemon( true );
            stderr.start();
            stdout.start();
            final Thread watcher = new Thread( threadGroup, forkable.name() + "-Watcher" )
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
                        ForkFault ex = new ForkFault( "Fork Watcher " + forkable.name() + "interrupted!", iex );
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
                            exitCallback.onError( "Fork " + forkable.name() + " exited with error, status was: " + status, null );
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
