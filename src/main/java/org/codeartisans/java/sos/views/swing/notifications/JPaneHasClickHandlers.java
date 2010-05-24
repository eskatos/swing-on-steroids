package org.codeartisans.java.sos.views.swing.notifications;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JPanel;
import org.codeartisans.java.sos.threading.WorkQueue;
import org.codeartisans.java.sos.views.handlers.ClickHandler;
import org.codeartisans.java.sos.views.notifications.ClickNotification;
import org.codeartisans.java.sos.views.handlers.HandlerRegistration;
import org.codeartisans.java.sos.views.handlers.HasClickHandlers;

public class JPaneHasClickHandlers
        implements HasClickHandlers<Void>
{

    private final JPanel panel;
    private final WorkQueue workQueue;

    public JPaneHasClickHandlers( WorkQueue workQueue, JPanel panel )
    {
        this.panel = panel;
        this.workQueue = workQueue;
    }

    @Override
    public HandlerRegistration addClickHandler( final ClickHandler<Void> handler )
    {
        final MouseListener listener = new MouseAdapter()
        {

            @Override
            public void mouseClicked( MouseEvent e )
            {
                workQueue.enqueue( new Runnable()
                {

                    @Override
                    public void run()
                    {
                        handler.onClick( new ClickNotification<Void>() );
                    }

                } );
            }

        };
        panel.addMouseListener( listener );
        return new HandlerRegistration()
        {

            @Override
            public void removeHandler()
            {
                panel.removeMouseListener( listener );
            }

        };
    }

    public JPanel getPanel()
    {
        return panel;
    }

}
