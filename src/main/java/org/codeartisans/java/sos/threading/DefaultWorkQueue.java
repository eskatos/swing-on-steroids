package org.codeartisans.java.sos.threading;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import java.util.LinkedList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.codeartisans.java.toolbox.StringUtils;

@Singleton
public final class DefaultWorkQueue
        implements WorkQueue
{

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultWorkQueue.class);
    private final PooledWorker[] threads;
    private final LinkedList<Runnable> queue;
    private final ThreadGroup threadGroup;

    @Inject
    DefaultWorkQueue(@Named(WorkQueue.NAME) String name, @Named(WorkQueue.SIZE) Integer size)
    {
        if (StringUtils.isEmpty(name)) {
            name = "Default";
        }
        queue = new LinkedList<Runnable>();
        threads = new PooledWorker[size];
        threadGroup = new ThreadGroup(name + "WorkQueue");
        for (int i = 0; i < size; i++) {
            threads[i] = new PooledWorker(threadGroup, name + "Worker-" + i);
            threads[i].start();
        }
    }

    @Override
    public void execute(Runnable r)
    {
        synchronized (queue) {
            queue.addLast(r);
            queue.notify();
        }
    }

    private class PooledWorker
            extends Thread
    {

        private PooledWorker(ThreadGroup threadGroup, String threadName)
        {
            super(threadGroup, threadName);
        }

        @Override
        public void run()
        {
            Runnable r;
            while (true) {
                synchronized (queue) {
                    while (queue.isEmpty()) {
                        try {
                            queue.wait();
                        } catch (InterruptedException ignored) {
                        }
                    }
                    r = queue.removeFirst();
                }
                // If we don't catch RuntimeException, the pool could leak threads
                try {
                    r.run();
                } catch (RuntimeException ex) {
                    LOGGER.warn(ex.getMessage(), ex);
                }
            }
        }

    }

}
