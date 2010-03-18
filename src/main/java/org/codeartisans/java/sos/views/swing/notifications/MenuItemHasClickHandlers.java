/*
 * Copyright (c) 2009 Paul Merlin <paul@nosphere.org>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.codeartisans.java.sos.views.swing.notifications;

import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.codeartisans.java.sos.threading.WorkQueue;
import org.codeartisans.java.sos.views.notifications.ClickNotification;
import org.codeartisans.java.sos.views.notifications.ClickHandler;
import org.codeartisans.java.sos.views.notifications.HandlerRegistration;
import org.codeartisans.java.sos.views.notifications.HasClickHandlers;

public final class MenuItemHasClickHandlers
        implements HasClickHandlers
{

    private final WorkQueue workQueue;
    private final MenuItem menuItem;

    public MenuItemHasClickHandlers( WorkQueue workQueue, MenuItem menuItem )
    {
        this.workQueue = workQueue;
        this.menuItem = menuItem;
    }

    @Override
    public HandlerRegistration addClickHandler( final ClickHandler handler )
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
                        handler.onClick( new ClickNotification() );
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
