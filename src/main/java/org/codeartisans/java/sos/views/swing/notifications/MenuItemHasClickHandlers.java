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

import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.codeartisans.java.sos.threading.WorkQueue;
import org.codeartisans.java.sos.views.notifications.ClickNotification;
import org.codeartisans.java.sos.views.handlers.ClickHandler;
import org.codeartisans.java.sos.views.handlers.HandlerRegistration;
import org.codeartisans.java.sos.views.handlers.HasClickHandlers;

public final class MenuItemHasClickHandlers
        implements HasClickHandlers<Void>
{

    private final WorkQueue workQueue;
    private final MenuItem menuItem;

    public MenuItemHasClickHandlers( WorkQueue workQueue, MenuItem menuItem )
    {
        this.workQueue = workQueue;
        this.menuItem = menuItem;
    }

    @Override
    public HandlerRegistration addClickHandler( final ClickHandler<Void> handler )
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
        menuItem.addActionListener( listener );
        return new HandlerRegistration()
        {

            @Override
            public void removeHandler()
            {
                menuItem.removeActionListener( listener );
            }

        };
    }

    public MenuItem getMenuItem()
    {
        return menuItem;
    }

}
