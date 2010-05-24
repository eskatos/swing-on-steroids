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

import java.awt.Window;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import org.codeartisans.java.sos.threading.WorkQueue;
import org.codeartisans.java.sos.views.handlers.FocusHandler;
import org.codeartisans.java.sos.views.handlers.HandlerRegistration;
import org.codeartisans.java.sos.views.handlers.HasFocusHandlers;

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
