package org.codeartisans.java.sos.sampleapp;

import org.codeartisans.java.sos.sampleapp.presentation.views.GreetingsView;
import org.codeartisans.java.sos.views.mock.notifications.MockHasClickHandler;
import org.codeartisans.java.sos.views.mock.notifications.StringHasStringValue;
import org.codeartisans.java.sos.views.notifications.HasClickHandlers;
import org.codeartisans.java.sos.views.values.HasValue;

public class MockGreetingsView
        implements GreetingsView
{

    final StringHasStringValue name = new StringHasStringValue();
    final MockHasClickHandler greet = new MockHasClickHandler();
    final StringHasStringValue message = new StringHasStringValue();
    final MockHasClickHandler close = new MockHasClickHandler();

    @Override
    public HasValue<String> nameInput()
    {
        return name;
    }

    @Override
    public HasClickHandlers greetButton()
    {
        return greet;
    }

    @Override
    public HasValue<String> messageDisplay()
    {
        return message;
    }

    @Override
    public HasClickHandlers closeButton()
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

}
