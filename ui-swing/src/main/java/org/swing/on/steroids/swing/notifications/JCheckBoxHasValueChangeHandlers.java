/*
 * Copyright (c) 2011, Paul Merlin. All Rights Reserved.
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

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JCheckBox;

import org.swing.on.steroids.threading.WorkQueue;
import org.swing.on.steroids.views.handlers.HandlerRegistration;
import org.swing.on.steroids.views.values.HasValueChangeHandlers;
import org.swing.on.steroids.views.values.ValueChangeHandler;
import org.swing.on.steroids.views.values.ValueChangeNotification;

public final class JCheckBoxHasValueChangeHandlers
        implements HasValueChangeHandlers<Boolean>
{

    private final JCheckBox checkBox;
    private final WorkQueue queue;

    public JCheckBoxHasValueChangeHandlers( WorkQueue queue, JCheckBox checkBox )
    {
        this.queue = queue;
        this.checkBox = checkBox;
    }

    @Override
    public HandlerRegistration addValueChangeHandler( final ValueChangeHandler<Boolean> handler )
    {
        final ItemListener listener = new ItemListener()
        {

            @Override
            public void itemStateChanged( final ItemEvent evt )
            {
                queue.enqueue( new Runnable()
                {

                    @Override
                    public void run()
                    {
                        handler.onValueChange( new ValueChangeNotification<Boolean>( evt.getStateChange() == ItemEvent.SELECTED ) );
                    }

                } );
            }

        };
        checkBox.addItemListener( listener );

        return new HandlerRegistration()
        {

            @Override
            public void removeHandler()
            {
                checkBox.removeItemListener( listener );
            }

        };
    }

    @Override
    public Boolean getValue()
    {
        return checkBox.isSelected();
    }

    @Override
    public void setValue( Boolean t )
    {
        checkBox.setSelected( t );
    }

}
