/*
 * Copyright (c) 2010, Fabien Barbero. All Rights Reserved.
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
