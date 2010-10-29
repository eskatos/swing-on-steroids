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

import java.awt.Image;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.codeartisans.java.sos.threading.WorkQueue;
import org.codeartisans.java.sos.views.values.ValueChangeHandler;
import org.codeartisans.java.sos.views.values.HasValueChangeHandlers;
import org.codeartisans.java.sos.views.values.ValueChangeNotification;
import org.codeartisans.java.sos.views.handlers.HandlerRegistration;
import org.codeartisans.java.sos.views.swing.components.EnhancedLabel;

public final class EnhancedLabelHasImageValueChangeHandlers
        extends EnhancedLabelHasImageValue
        implements HasValueChangeHandlers<Image>
{

    private final WorkQueue workQueue;

    public EnhancedLabelHasImageValueChangeHandlers( WorkQueue workQueue, EnhancedLabel enhancedLabel )
    {
        super( enhancedLabel );
        this.workQueue = workQueue;
    }

    @Override
    public HandlerRegistration addValueChangeHandler( final ValueChangeHandler<Image> handler )
    {
        final PropertyChangeListener propertyChangeListener = new PropertyChangeListener()
        {

            @Override
            public void propertyChange( PropertyChangeEvent evt )
            {
                if ( "icon".equals( evt.getPropertyName() ) ) {
                    workQueue.enqueue( new Runnable()
                    {

                        @Override
                        public void run()
                        {
                            handler.onValueChange( new ValueChangeNotification<Image>( enhancedLabel.getImage() ) );
                        }

                    } );
                }
            }

        };
        enhancedLabel.addPropertyChangeListener( propertyChangeListener );
        return new HandlerRegistration()
        {

            @Override
            public void removeHandler()
            {
                enhancedLabel.removePropertyChangeListener( propertyChangeListener );
            }

        };
    }

}
