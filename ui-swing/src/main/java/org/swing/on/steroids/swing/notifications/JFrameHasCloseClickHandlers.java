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

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JFrame;

import org.swing.on.steroids.threading.WorkQueue;
import org.swing.on.steroids.views.handlers.ClickHandler;
import org.swing.on.steroids.views.notifications.ClickNotification;
import org.swing.on.steroids.views.handlers.HandlerRegistration;
import org.swing.on.steroids.views.handlers.HasClickHandlers;

public final class JFrameHasCloseClickHandlers
        implements HasClickHandlers<Void>
{

    private final WorkQueue workQueue;
    private final JFrame frame;

    public JFrameHasCloseClickHandlers( WorkQueue workQueue, JFrame frame )
    {
        this.workQueue = workQueue;
        this.frame = frame;
    }

    @Override
    public HandlerRegistration addClickHandler( final ClickHandler<Void> handler )
    {
        final WindowListener listener = new WindowAdapter()
        {

            @Override
            public void windowClosing( WindowEvent e )
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
        frame.addWindowListener( listener );
        return new HandlerRegistration()
        {

            @Override
            public void removeHandler()
            {
                frame.removeWindowListener( listener );
            }

        };
    }

    public JFrame getJFrame()
    {
        return frame;
    }

}
