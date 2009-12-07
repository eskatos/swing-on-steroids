package org.codeartisans.java.sos.threading;

public interface WorkQueue
{

    static final String NAME = "WorkQueueName";
    static final String SIZE = "WorkQueueSize";

    void execute(Runnable runnable);

}
