package org.codeartisans.java.sos.views.swing.notifications;

import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.codeartisans.java.sos.views.notifications.ClickNotification;
import org.codeartisans.java.sos.views.notifications.ClickHandler;
import org.codeartisans.java.sos.views.notifications.HandlerRegistration;
import org.codeartisans.java.sos.views.notifications.HasClickHandlers;

public final class MenuItemHasClickHandlers
        implements HasClickHandlers
{

    private final MenuItem menuItem;

    public MenuItemHasClickHandlers(MenuItem menuItem)
    {
        this.menuItem = menuItem;
    }

    public HandlerRegistration addClickHandler(final ClickHandler handler)
    {
        final ActionListener listener = new ActionListener()
        {

            public void actionPerformed(ActionEvent e)
            {
                handler.onClick(new ClickNotification());
            }
        };
        menuItem.addActionListener(listener);
        return new HandlerRegistration()
        {

            public void removeHandler()
            {
                menuItem.removeActionListener(listener);
            }
        };
    }
}
