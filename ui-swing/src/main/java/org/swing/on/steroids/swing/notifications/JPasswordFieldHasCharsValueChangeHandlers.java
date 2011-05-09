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

import javax.swing.JPasswordField;
import javax.swing.event.DocumentListener;

import org.swing.on.steroids.threading.WorkQueue;
import org.swing.on.steroids.views.values.ValueChangeHandler;
import org.swing.on.steroids.views.values.HasValueChangeHandlers;
import org.swing.on.steroids.views.values.ValueChangeNotification;
import org.swing.on.steroids.views.handlers.HandlerRegistration;

public final class JPasswordFieldHasCharsValueChangeHandlers
        extends JPasswordFieldHasCharsValue
        implements HasValueChangeHandlers<char[]>
{

    private final WorkQueue workQueue;

    public JPasswordFieldHasCharsValueChangeHandlers( WorkQueue workQueue, JPasswordField passwordField )
    {
        super( passwordField );
        this.workQueue = workQueue;
    }

    @Override
    public HandlerRegistration addValueChangeHandler( final ValueChangeHandler<char[]> handler )
    {
        final DocumentListener docListener = new TextDocumentListener()
        {

            @Override
            protected void onValueChange()
            {
                workQueue.enqueue( new Runnable()
                {

                    @Override
                    public void run()
                    {
                        handler.onValueChange( new ValueChangeNotification<char[]>( passwordField.getPassword() ) );
                    }

                } );
            }

        };
        passwordField.getDocument().addDocumentListener( docListener );
        return new HandlerRegistration()
        {

            @Override
            public void removeHandler()
            {
                passwordField.getDocument().removeDocumentListener( docListener );
            }

        };
    }

}
