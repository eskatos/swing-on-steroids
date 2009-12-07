package org.codeartisans.java.sos.forking;

import java.io.File;
import java.util.List;
import org.codeartisans.java.toolbox.async.AsyncCallbackWithE;

public interface ForkService
{

    public enum ShutdownAction
    {

        KILL, LET_LIVE
    }

    void fork(List<String> command, ShutdownAction shutdownAction);

    void fork(List<String> command, File workingDirectory, ShutdownAction shutdownAction);

    void fork(List<String> command, AsyncCallbackWithE<Void, ForkFault> exitCallback, ShutdownAction shutdownAction);

    void fork(List<String> command, File workingDirectory, AsyncCallbackWithE<Void, ForkFault> exitCallback, ShutdownAction shutdownAction);

}
