package org.codeartisans.java.sos.views.swing.helpers;

import javax.swing.SwingUtilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.codeartisans.java.sos.views.swing.components.ExceptionDialog;

public final class SteroidUncaughtExceptionHandler
        implements Thread.UncaughtExceptionHandler
{

    private static final Logger LOGGER = LoggerFactory.getLogger(SteroidUncaughtExceptionHandler.class);

    public void uncaughtException(final Thread t, final Throwable ex)
    {
        if (SwingUtilities.isEventDispatchThread()) {
            showException(ex);
        } else {
            SwingUtilities.invokeLater(new Runnable()
            {

                public void run()
                {
                    showException(ex);
                }
            });
        }


    }

    private void showException(Throwable ex)
    {
        LOGGER.error("UncaughtException handled: " + ex.getMessage(), ex);
        ExceptionDialog.showException(ex);
    }
}
