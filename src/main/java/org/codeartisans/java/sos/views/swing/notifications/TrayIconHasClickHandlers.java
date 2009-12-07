package org.codeartisans.java.sos.views.swing.notifications;

import java.awt.TrayIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import org.codeartisans.java.sos.views.notifications.ClickNotification;
import org.codeartisans.java.sos.views.notifications.ClickHandler;
import org.codeartisans.java.sos.views.notifications.HandlerRegistration;
import org.codeartisans.java.sos.views.notifications.HasClickHandlers;

public final class TrayIconHasClickHandlers
        implements HasClickHandlers
{

    private final TrayIcon trayIcon;

    public TrayIconHasClickHandlers(TrayIcon trayIcon)
    {
        this.trayIcon = trayIcon;
    }

    public HandlerRegistration addClickHandler(final ClickHandler handler)
    {
        final MouseListener listener = new MouseAdapter()
        {

            @Override
            public void mouseReleased(MouseEvent e)
            {
                handler.onClick(new ClickNotification());
            }
        };
        trayIcon.addMouseListener(listener);
        return new HandlerRegistration()
        {

            public void removeHandler()
            {
                trayIcon.removeMouseListener(listener);
            }
        };
    }
}
