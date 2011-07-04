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
import java.util.Map;
import javax.swing.JToggleButton;

import org.swing.on.steroids.threading.WorkQueue;
import org.swing.on.steroids.views.handlers.HandlerRegistration;
import org.swing.on.steroids.views.values.HasValueChangeHandlers;
import org.swing.on.steroids.views.values.ValueChangeHandler;
import org.swing.on.steroids.views.values.ValueChangeNotification;

public class JToggleButtonGroupHasValueChangeHandlers<T>
        implements HasValueChangeHandlers<T>
{

    private final WorkQueue queue;
    private final Map<JToggleButton, T> toggleButtons;
    private T currentValue;

    public JToggleButtonGroupHasValueChangeHandlers( WorkQueue queue, Map<JToggleButton, T> toggleButtons )
    {
        this.queue = queue;
        this.toggleButtons = toggleButtons;
    }

    @Override
    public final HandlerRegistration addValueChangeHandler( final ValueChangeHandler<T> handler )
    {
        final ItemListener listener = new ItemListener()
        {

            @Override
            public void itemStateChanged( final ItemEvent evt )
            {
                int state = evt.getStateChange();
                JToggleButton source = ( JToggleButton ) evt.getSource();
                if ( state == ItemEvent.SELECTED ) {
                    // Selected
                    currentValue = toggleButtons.get( source );
                    for ( Map.Entry<JToggleButton, T> eachEntry : toggleButtons.entrySet() ) {
                        if ( !eachEntry.getValue().equals( currentValue ) ) {
                            eachEntry.getKey().removeItemListener( this );
                            eachEntry.getKey().setSelected( false );
                            eachEntry.getKey().addItemListener( this );
                        }
                    }
                } else {
                    // Deselected
                    currentValue = null;
                }
                queue.enqueue( new Runnable()
                {

                    @Override
                    public void run()
                    {
                        handler.onValueChange( new ValueChangeNotification<T>( currentValue ) );
                    }

                } );
            }

        };
        for ( JToggleButton eachButton : toggleButtons.keySet() ) {
            eachButton.addItemListener( listener );
        }
        return new HandlerRegistration()
        {

            @Override
            public void removeHandler()
            {
                for ( JToggleButton eachButton : toggleButtons.keySet() ) {
                    eachButton.removeItemListener( listener );
                }
            }

        };
    }

    @Override
    public final T getValue()
    {
        return currentValue;
    }

    @Override
    public final void setValue( T t )
    {
        for ( Map.Entry<JToggleButton, T> eachEntry : toggleButtons.entrySet() ) {
            eachEntry.getKey().setSelected( eachEntry.getValue().equals( t ) );
        }
        currentValue = t;
    }

}
