/*
 * Copyright (c) 2010, Jean-Michel Tonneau. All Rights Reserved.
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

import javax.swing.JComboBox;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.swing.on.steroids.threading.WorkQueue;
import org.swing.on.steroids.views.values.ValueChangeHandler;
import org.swing.on.steroids.views.values.HasValueChangeHandlers;
import org.swing.on.steroids.views.values.ValueChangeNotification;
import org.swing.on.steroids.views.handlers.HandlerRegistration;

/**
 * @param <V> Value type
 * @author jean-Michel Tonneau <barsifedron@gmail.com>
 */
public final class JComboBoxHasValueChangeHandlers<V>
        extends JComboBoxHasValue<V>
        implements HasValueChangeHandlers<V>
{

    private WorkQueue workQueue;

    public JComboBoxHasValueChangeHandlers( WorkQueue workQueue, JComboBox jComboBox )
    {
        super( jComboBox );
        this.workQueue = workQueue;
    }

    @Override
    public HandlerRegistration addValueChangeHandler( final ValueChangeHandler<V> handler )
    {
        final ActionListener actionListener = new ActionListener()
        {

            @Override
            public void actionPerformed( ActionEvent e )
            {
                workQueue.enqueue( new Runnable()
                {

                    @Override
                    public void run()
                    {
                        handler.onValueChange( new ValueChangeNotification<V>( ( V ) jComboBox.getSelectedItem() ) );
                    }

                } );
            }

        };
        jComboBox.addActionListener( actionListener );
        return new HandlerRegistration()
        {

            @Override
            public void removeHandler()
            {
                jComboBox.removeActionListener( actionListener );
            }

        };
    }

}
