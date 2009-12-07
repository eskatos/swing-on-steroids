package org.codeartisans.java.sos.views.swing.helpers;

public final class SteroidAwtHandler
{

    public void handle(Throwable t)
    {
        new SteroidUncaughtExceptionHandler().uncaughtException(Thread.currentThread(), t);
    }
}
