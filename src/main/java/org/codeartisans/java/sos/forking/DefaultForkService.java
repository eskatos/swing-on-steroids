package org.codeartisans.java.sos.forking;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.codeartisans.java.toolbox.async.AsyncCallbackWithE;

public final class DefaultForkService
        implements ForkService
{

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultForkService.class);
    private CopyOnWriteArrayList<Process> processToKillOnShutdown;
    private boolean shutdownHookAdded = false;
    private final ThreadGroup threadGroup;

    DefaultForkService()
    {
        threadGroup = new ThreadGroup("ForkServiceThreads");
    }

    @Override
    public void fork(List<String> command, ShutdownAction shutdownAction)
    {
        fork(command, null, null, shutdownAction);
    }

    @Override
    public void fork(List<String> command, File workingDirectory, ShutdownAction shutdownAction)
    {
        fork(command, workingDirectory, null, shutdownAction);
    }

    @Override
    public void fork(List<String> command, AsyncCallbackWithE<Void, ForkFault> exitCallback, ShutdownAction shutdownAction)
    {
        fork(command, null, exitCallback, shutdownAction);
    }

    @Override
    public void fork(List<String> command, File workingDirectory, final AsyncCallbackWithE<Void, ForkFault> exitCallback,
                     final ShutdownAction shutdownAction)
    {
        try {
            final String forkUuid = UUID.randomUUID().toString();
            final ProcessBuilder builder = new ProcessBuilder(command);
            builder.directory(workingDirectory);
            final Process proc = builder.start();
            final StreamGobbler stderr = new StreamGobbler(proc.getErrorStream(), threadGroup,
                                                           forkUuid + "-STDERR-Gobbler");
            final StreamGobbler stdout = new StreamGobbler(proc.getInputStream(), threadGroup,
                                                           forkUuid + "-STDOUT-Gobbler");
            stderr.setDaemon(true);
            stdout.setDaemon(true);
            stderr.start();
            stdout.start();
            final Thread watcher = new Thread(threadGroup, forkUuid + "-Watcher")
            {

                @Override
                public void run()
                {
                    try {
                        if (shutdownAction == ShutdownAction.KILL) {
                            ensureShutdownHook();
                            addKillOnShutdownProcess(proc);
                        }
                        proc.waitFor();
                        stderr.interrupt();
                        stdout.interrupt();
                        if (shutdownAction == ShutdownAction.KILL) {
                            removeKillOnShutdownProcess(proc);
                        }
                        int status = proc.exitValue();
                        if (status == 0) {
                            if (exitCallback != null) {
                                exitCallback.onSuccess(null);
                            }
                        } else {
                            if (exitCallback != null) {
                                exitCallback.onError("Fork " + forkUuid + " exited with error, status was: " + status,
                                                     null);
                            }
                        }
                        this.interrupt();
                    } catch (InterruptedException ex) {
                        if (exitCallback != null) {
                            exitCallback.onError("Fork Watcher " + forkUuid + "interrupted!", new ForkFault(ex.getMessage()));
                        }
                    }
                }

            };
            watcher.setDaemon(true);
            watcher.start();
        } catch (IOException ex) {
            LOGGER.error(ex.getMessage(), ex);
            throw new ForkFault("Unable to fork: " + ex.getMessage());
        }
    }

    private void addKillOnShutdownProcess(Process proc)
    {
        if (processToKillOnShutdown == null) {
            processToKillOnShutdown = new CopyOnWriteArrayList<Process>();
        }
        processToKillOnShutdown.add(proc);
    }

    private void removeKillOnShutdownProcess(Process proc)
    {
        if (processToKillOnShutdown != null) {
            processToKillOnShutdown.remove(proc);
        }
    }

    private synchronized void ensureShutdownHook()
    {
        if (!shutdownHookAdded) {
            Runtime.getRuntime().addShutdownHook(new Thread(threadGroup, new Runnable()
            {

                @Override
                public void run()
                {
                    if (processToKillOnShutdown != null) {
                        for (Process eachProcessToKill : processToKillOnShutdown) {
                            eachProcessToKill.destroy();
                            LOGGER.debug("Forked process destroyed: " + eachProcessToKill.toString());
                        }
                    }
                }

            }, "ShutdownHook"));
            shutdownHookAdded = true;
        }
    }

}
