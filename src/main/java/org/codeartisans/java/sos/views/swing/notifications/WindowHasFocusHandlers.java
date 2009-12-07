package org.codeartisans.java.sos.views.swing.notifications;

import java.awt.Window;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import org.codeartisans.java.sos.views.notifications.FocusHandler;
import org.codeartisans.java.sos.views.notifications.HandlerRegistration;
import org.codeartisans.java.sos.views.notifications.HasFocusHandlers;

public class WindowHasFocusHandlers
        implements HasFocusHandlers
{

    private final Window window;

    public WindowHasFocusHandlers(Window window)
    {
        this.window = window;
    }

    public HandlerRegistration addFocusHandler(final FocusHandler handler)
    {
        final WindowFocusListener listener = new WindowFocusListener()
        {

            public void windowGainedFocus(WindowEvent e)
            {
                handler.onFocusGained();
            }

            public void windowLostFocus(WindowEvent e)
            {
                handler.onFocusLost();
            }
        };
        window.addWindowFocusListener(listener);
        return new HandlerRegistration()
        {

            public void removeHandler()
            {
                window.removeWindowFocusListener(listener);
            }
        };
    }
}
