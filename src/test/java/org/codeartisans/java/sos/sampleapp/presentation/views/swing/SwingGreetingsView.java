/*
 * Copyright (c) 2009 Paul Merlin <paul@nosphere.org>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.codeartisans.java.sos.sampleapp.presentation.views.swing;

import com.google.inject.Inject;
import java.awt.EventQueue;
import javax.swing.JFrame;
import org.codeartisans.java.sos.sampleapp.presentation.views.GreetingsView;
import org.codeartisans.java.sos.views.handlers.HasClickHandlers;
import org.codeartisans.java.sos.views.swing.SwingWrappersFactory;
import org.codeartisans.java.sos.views.swing.notifications.JFrameHasCloseClickHandlers;
import org.codeartisans.java.sos.views.swing.notifications.JLabelHasStringValue;
import org.codeartisans.java.sos.views.swing.notifications.JTextComponentHasStringValue;
import org.codeartisans.java.sos.views.values.HasValue;

public class SwingGreetingsView
        implements GreetingsView
{

    private final SwingWrappersFactory swingWrappersFactory;
    private GreetingsFrame delegate;
    private HasClickHandlers greetButton;
    private HasClickHandlers closeButton;

    @Inject
    public SwingGreetingsView(SwingWrappersFactory swingWrapFactory)
    {
        swingWrappersFactory = swingWrapFactory;
        EventQueue.invokeLater(new Runnable()
        {

            @Override
            public void run()
            {
                delegate = new GreetingsFrame();
                delegate.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                greetButton = swingWrappersFactory.createJButtonHasClickHandler(delegate.getButton());
                closeButton = swingWrappersFactory.createJFrameHasCloseClickHandlers(delegate);
            }

        });

    }

    @Override
    public HasValue<String> nameInput()
    {
        return new JTextComponentHasStringValue(delegate.getInput());
    }

    @Override
    public HasClickHandlers greetButton()
    {
        return greetButton;
    }

    @Override
    public HasValue<String> messageDisplay()
    {
        return new JLabelHasStringValue(delegate.getMessage());
    }

    @Override
    public HasClickHandlers closeButton()
    {
        return closeButton;
    }

    @Override
    public void reveal()
    {
        EventQueue.invokeLater(new Runnable()
        {

            @Override
            public void run()
            {
                delegate.setVisible(true);
            }

        });
    }

    @Override
    public void hide()
    {
        EventQueue.invokeLater(new Runnable()
        {

            @Override
            public void run()
            {
                delegate.dispose();
            }

        });
    }

    @Override
    public void busy()
    {
        EventQueue.invokeLater(new Runnable()
        {

            @Override
            public void run()
            {
                delegate.getButton().setEnabled(false);
            }

        });
    }

    @Override
    public void done()
    {
        EventQueue.invokeLater(new Runnable()
        {

            @Override
            public void run()
            {
                delegate.getButton().setEnabled(true);
            }

        });
    }

}
