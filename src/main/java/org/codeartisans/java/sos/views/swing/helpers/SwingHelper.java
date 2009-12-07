package org.codeartisans.java.sos.views.swing.helpers;

import java.lang.reflect.InvocationTargetException;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.RepaintManager;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * Collection of static methods dealing with Swing application init.
 */
public final class SwingHelper
{

    private SwingHelper()
    {
    }

    /**
     * Register enforcers to ensure the EDT stays clean.
     */
    public static void initSafeSwing()
    {
        RepaintManager.setCurrentManager(new CheckThreadViolationRepaintManager());
        EventDispatchThreadHangMonitor.initMonitoring();
    }

    /**
     * Register SwingHelper UncaughtExceptionHandler and an AwtHandler for modal dialogs.
     */
    public static void initExceptionHandling()
    {
        Thread.setDefaultUncaughtExceptionHandler(new SteroidUncaughtExceptionHandler());
        System.setProperty("sun.awt.exception.handler", SteroidAwtHandler.class.getName());
    }

    /**
     * Init given Look And Feel.
     * @param className LAF class name
     */
    public static void initLookAndFeel(final String className)
    {
        try {
            SwingUtilities.invokeAndWait(new Runnable()
            {

                public void run()
                {
                    try {
                        JFrame.setDefaultLookAndFeelDecorated(true);
                        JDialog.setDefaultLookAndFeelDecorated(true);
                        UIManager.setLookAndFeel(className);
                    } catch (Exception ex) {
                        throw new RuntimeException(ex.getMessage(), ex);
                    }
                }
            });
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex.getMessage(), ex);
        } catch (InvocationTargetException ex) {
            throw new RuntimeException(ex.getMessage(), ex);
        }
    }
}
