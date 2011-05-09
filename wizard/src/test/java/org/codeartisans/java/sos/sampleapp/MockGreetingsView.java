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
package org.codeartisans.java.sos.sampleapp;

import org.codeartisans.java.sos.sampleapp.presentation.views.GreetingsView;
import org.swing.on.steroids.views.handlers.MockHasClickHandler;
import org.swing.on.steroids.views.values.StringHasStringValue;
import org.swing.on.steroids.views.handlers.HasClickHandlers;
import org.swing.on.steroids.views.values.HasValue;

public class MockGreetingsView
        implements GreetingsView
{

    final StringHasStringValue name = new StringHasStringValue();
    final MockHasClickHandler<Void> greet = new MockHasClickHandler<Void>();
    final StringHasStringValue message = new StringHasStringValue();
    final MockHasClickHandler<Void> close = new MockHasClickHandler<Void>();

    @Override
    public HasValue<String> nameInput()
    {
        return name;
    }

    @Override
    public HasClickHandlers<Void> greetButton()
    {
        return greet;
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

    @Override
    public void doSomethingLongInEDT()
    {
    }

}
