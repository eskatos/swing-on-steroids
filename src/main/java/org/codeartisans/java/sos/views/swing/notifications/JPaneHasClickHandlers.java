/*
 * Copyright (c) 2009, Paul Merlin. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
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
