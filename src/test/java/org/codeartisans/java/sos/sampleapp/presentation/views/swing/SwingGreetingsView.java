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
package org.codeartisans.java.sos.sampleapp.presentation.views.swing;

import com.google.inject.Inject;

import java.awt.EventQueue;
import javax.swing.JFrame;

import org.codeartisans.java.sos.sampleapp.presentation.views.GreetingsView;
import org.codeartisans.java.sos.views.handlers.HasClickHandlers;
import org.codeartisans.java.sos.views.swing.SwingWrappersFactory;
import org.codeartisans.java.sos.views.swing.annotations.EventDispatchThread;
import org.codeartisans.java.sos.views.swing.annotations.EventDispatchThreadPolicy;
import org.codeartisans.java.sos.views.swing.helpers.SwingHelper;
import org.codeartisans.java.sos.views.swing.notifications.JLabelHasStringValue;
import org.codeartisans.java.sos.views.values.HasValue;
import org.codeartisans.java.sos.views.values.HasValueChangeHandlers;

public class SwingGreetingsView
        implements GreetingsView
{

    private final SwingWrappersFactory swingWrappersFactory;
    private GreetingsFrame delegate;
    private HasClickHandlers<Void> greetButton;
    private HasClickHandlers<Void> closeButton;
    private HasValueChangeHandlers<String> nameInput;
    private JLabelHasStringValue messageDisplay;

    @Inject
    public SwingGreetingsView( SwingWrappersFactory swingWrapFactory )
    {
        swingWrappersFactory = swingWrapFactory;
        SwingHelper.invokeAndWait( new Runnable()
        {

            @Override
            public void run()
            {
                delegate = new GreetingsFrame();
                delegate.setDefaultCloseOperation( JFrame.DO_NOTHING_ON_CLOSE );
                greetButton = swingWrappersFactory.createJButtonHasClickHandler( delegate.getButton() );
                closeButton = swingWrappersFactory.createJFrameHasCloseClickHandlers( delegate );
                nameInput = swingWrappersFactory.createJTextComponentHasStringValueChangeHandlers( delegate.getInput() );
                messageDisplay = new JLabelHasStringValue( delegate.getMessage() );
            }

        } );

    }

    @Override
    public HasValue<String> nameInput()
    {
        return nameInput;
    }

    @Override
    public HasClickHandlers<Void> greetButton()
    {
        return greetButton;
    }

    @Override
    public HasValue<String> messageDisplay()
    {
        return messageDisplay;
    }

    @Override
    public HasClickHandlers<Void> closeButton()
    {
        return closeButton;
    }

    @Override
    @EventDispatchThread( value = EventDispatchThreadPolicy.invokeAndWait )
    public void doSomethingLongInEDT()
    {
        for ( int i = 0; i < 200; i++ ) {
            delegate.getMessage().setText( "Coucou " + i );
        }
    }

    @Override
    public void reveal()
    {
        EventQueue.invokeLater( new Runnable()
        {

            @Override
            public void run()
            {
                delegate.setVisible( true );
            }

        } );
    }

    @Override
    public void hide()
    {
        EventQueue.invokeLater( new Runnable()
        {

            @Override
            public void run()
            {
                delegate.dispose();
            }

        } );
    }

    @Override
    public void busy()
    {
        EventQueue.invokeLater( new Runnable()
        {

            @Override
            public void run()
            {
                delegate.getButton().setEnabled( false );
            }

        } );
    }

    @Override
    public void done()
    {
        EventQueue.invokeLater( new Runnable()
        {

            @Override
            public void run()
            {
                delegate.getButton().setEnabled( true );
            }

        } );
    }

}
