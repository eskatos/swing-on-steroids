package org.codeartisans.java.sos.views.swing.notifications;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import org.codeartisans.java.sos.views.notifications.ClickNotification;
import org.codeartisans.java.sos.views.notifications.ClickHandler;
import org.codeartisans.java.sos.views.notifications.HandlerRegistration;
import org.codeartisans.java.sos.views.notifications.HasClickHandlers;

public final class JButtonHasClickHandlers
        implements HasClickHandlers
{

    private final JButton button;

    public JButtonHasClickHandlers(JButton button)
    {
        this.button = button;
    }

    @Override
    public HandlerRegistration addClickHandler(final ClickHandler handler)
    {
        final ActionListener listener = new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                handler.onClick(new ClickNotification());
            }

        };
        button.addActionListener(listener);
        return new HandlerRegistration()
        {

            @Override
            public void removeHandler()
            {
                button.removeActionListener(listener);
            }

        };

    }

}
