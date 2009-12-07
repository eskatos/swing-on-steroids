package org.codeartisans.java.sos.forking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;

public final class StreamGobbler
        extends Thread
{

    private InputStream is;
    private OutputStream redirect;

    public StreamGobbler(InputStream is, ThreadGroup threadGroup, String name)
    {
        super(threadGroup, name);
        this.is = is;
    }

    public StreamGobbler(InputStream is, ThreadGroup threadGroup, String name, OutputStream redirect)
    {
        this(is, threadGroup, name);
        this.redirect = redirect;
    }

    @Override
    public void run()
    {
        try {
            PrintWriter pw = null;
            if (redirect != null) {
                pw = new PrintWriter(redirect);
            }
            final BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line = null;
            while ((line = br.readLine()) != null) {
                if (pw != null) {
                    pw.println(line);
                }
            }
            if (pw != null) {
                pw.flush();
            }
            br.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}

