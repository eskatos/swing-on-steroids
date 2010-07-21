package org.codeartisans.java.sos.sampleapp;

import org.codeartisans.java.sos.sampleapp.presentation.views.GreetingsView;
import org.codeartisans.java.sos.views.notifications.MockHasClickHandler;
import org.codeartisans.java.sos.views.values.StringHasStringValue;
import org.codeartisans.java.sos.views.handlers.HasClickHandlers;
import org.codeartisans.java.sos.views.values.HasValue;

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
