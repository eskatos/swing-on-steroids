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

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Collection;
import javax.swing.JTree;
import javax.swing.tree.TreePath;

import org.codeartisans.java.sos.threading.WorkQueue;
import org.codeartisans.java.sos.views.handlers.HandlerRegistration;
import org.codeartisans.java.sos.views.values.HasValueChangeHandlers;
import org.codeartisans.java.sos.views.values.ValueChangeHandler;
import org.codeartisans.java.sos.views.values.ValueChangeNotification;

/**
 * @author Fabien Barbero <fabien.barbero@gmail.com>
 */
public class JTreeHasValueChangeHandlers
        extends JTreeHasValue
        implements HasValueChangeHandlers<Collection<?>>
{

    private final WorkQueue workQueue;

    public JTreeHasValueChangeHandlers( WorkQueue workQueue, JTree tree )
    {
        super( tree );
        this.workQueue = workQueue;
    }

    @Override
    public HandlerRegistration addValueChangeHandler( final ValueChangeHandler<Collection<?>> handler )
    {
        final MouseListener mouseListener = new MouseAdapter()
        {

            @Override
            public void mouseReleased( MouseEvent e )
            {
                if ( e.getButton() == MouseEvent.BUTTON1 ) {
                    workQueue.enqueue( new Runnable()
                    {

                        @Override
                        public void run()
                        {
                            TreePath[] paths = tree.getSelectionPaths();
                            if ( paths != null && paths.length != 0 ) {
                                handler.onValueChange( new ValueChangeNotification<Collection<?>>( getValue() ) );
                            }
                        }

                    } );
                }
            }

        };

        tree.addMouseListener( mouseListener );


        return new HandlerRegistration()
        {

            @Override
            public void removeHandler()
            {
                tree.removeMouseListener( mouseListener );
            }

        };
    }

}
