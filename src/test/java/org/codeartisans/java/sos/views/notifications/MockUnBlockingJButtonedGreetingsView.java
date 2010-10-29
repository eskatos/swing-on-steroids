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
package org.codeartisans.java.sos.views.notifications;

import com.google.inject.Inject;

import javax.swing.JButton;

import org.codeartisans.java.sos.views.handlers.HasClickHandlers;
import org.codeartisans.java.sos.views.values.StringHasStringValue;
import org.codeartisans.java.sos.sampleapp.presentation.views.GreetingsView;
import org.codeartisans.java.sos.views.swing.SwingWrappersFactory;
import org.codeartisans.java.sos.views.values.HasValue;

class MockUnBlockingJButtonedGreetingsView
        implements GreetingsView
{

    private final SwingWrappersFactory swingWrappersFactory;
    final StringHasStringValue name = new StringHasStringValue();
    final MockHasClickHandler<Void> greet = new MockHasClickHandler<Void>();
    final StringHasStringValue message = new StringHasStringValue();
    final MockHasClickHandler<Void> close = new MockHasClickHandler<Void>();
    final JButton button = new JButton();

    @Inject
    public MockUnBlockingJButtonedGreetingsView( SwingWrappersFactory swingWrappersFactory )
    {
        this.swingWrappersFactory = swingWrappersFactory;
    }

    @Override
    public HasValue<String> nameInput()
    {
        return name;
    }

    @Override
    public HasClickHandlers<Void> greetButton()
    {
        return swingWrappersFactory.createJButtonHasClickHandler( button );
    }

    @Override
    public HasValue<String> messageDisplay()
    {
        return message;
    }

    @Override
    public HasClickHandlers<Void> closeButton()
    {
        return close;
    }

    @Override
    public void reveal()
    {
    }

    @Override
    public void hide()
    {
    }

    @Override
    public void busy()
    {
    }

    @Override
    public void done()
    {
    }

    public JButton getButton()
    {
        return button;
    }

    @Override
    public void doSomethingLongInEDT()
    {
    }

}
