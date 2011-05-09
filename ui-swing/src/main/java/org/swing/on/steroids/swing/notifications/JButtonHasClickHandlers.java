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
package org.swing.on.steroids.swing.notifications;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

import org.swing.on.steroids.threading.WorkQueue;
import org.swing.on.steroids.views.notifications.ClickNotification;
import org.swing.on.steroids.views.handlers.ClickHandler;
import org.swing.on.steroids.views.handlers.HandlerRegistration;
import org.swing.on.steroids.views.handlers.HasClickHandlers;

public class JButtonHasClickHandlers
        implements HasClickHandlers<Void>
{

    private final WorkQueue workQueue;
    private final JButton button;

    public JButtonHasClickHandlers( WorkQueue workQueue, JButton button )
    {
        this.workQueue = workQueue;
        this.button = button;
    }

    @Override
    public final HandlerRegistration addClickHandler( final ClickHandler<Void> handler )
    {
        final ActionListener listener = new ActionListener()
        {

            @Override
            public void actionPerformed( ActionEvent e )
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
        button.addActionListener( listener );
        return new HandlerRegistration()
        {

            @Override
            public void removeHandler()
            {
                button.removeActionListener( listener );
            }

        };
    }

    public final JButton getJButton()
    {
        return button;
    }

}
