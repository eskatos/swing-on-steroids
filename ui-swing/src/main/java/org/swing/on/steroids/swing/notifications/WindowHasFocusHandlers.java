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

import java.awt.Window;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

import org.swing.on.steroids.threading.WorkQueue;
import org.swing.on.steroids.views.handlers.FocusHandler;
import org.swing.on.steroids.views.handlers.HandlerRegistration;
import org.swing.on.steroids.views.handlers.HasFocusHandlers;

public final class WindowHasFocusHandlers
        implements HasFocusHandlers
{

    private final Window window;
    private final WorkQueue workQueue;

    public WindowHasFocusHandlers( WorkQueue workQueue, Window window )
    {
        this.workQueue = workQueue;
        this.window = window;
    }

    @Override
    public HandlerRegistration addFocusHandler( final FocusHandler handler )
    {
        final WindowFocusListener listener = new WindowFocusListener()
        {

            @Override
            public void windowGainedFocus( WindowEvent e )
            {
                handleFocusGained( handler );
            }

            @Override
            public void windowLostFocus( WindowEvent e )
            {
                handleFocusLost( handler );
            }

        };
        window.addWindowFocusListener( listener );
        return new HandlerRegistration()
        {

            @Override
            public void removeHandler()
            {
                window.removeWindowFocusListener( listener );
            }

        };
    }

    private void handleFocusGained( final FocusHandler handler )
    {
        workQueue.enqueue( new Runnable()
        {

            @Override
            public void run()
            {
                handler.onFocusGained();
            }

        } );
    }

    private void handleFocusLost( final FocusHandler handler )
    {
        workQueue.enqueue( new Runnable()
        {

            @Override
            public void run()
            {
                handler.onFocusLost();
            }

        } );
    }

}
