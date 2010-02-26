package org.codeartisans.java.sos.forking;

import java.io.File;
import java.util.List;

public interface Forkable
{

    List<String> command();

    /**
     * @return Wanted working directory. This can be null, in this case, the current working directory will be used.
     */
    File workingDirectory();
}