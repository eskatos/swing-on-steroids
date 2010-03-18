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
package org.codeartisans.java.sos.views.notifications;

import org.codeartisans.java.sos.views.values.StringHasStringValue;
import com.google.inject.Inject;
import javax.swing.JButton;
import org.codeartisans.java.sos.sampleapp.presentation.views.GreetingsView;
import org.codeartisans.java.sos.views.swing.SwingWrappersFactory;
import org.codeartisans.java.sos.views.values.HasValue;

class MockUnBlockingJButtonedGreetingsView implements GreetingsView {

    private final SwingWrappersFactory swingWrappersFactory;
    final StringHasStringValue name = new StringHasStringValue();
    final MockHasClickHandler greet = new MockHasClickHandler();
    final StringHasStringValue message = new StringHasStringValue();
    final MockHasClickHandler close = new MockHasClickHandler();
    final JButton button = new JButton();

    @Inject
    public MockUnBlockingJButtonedGreetingsView(SwingWrappersFactory swingWrappersFactory) {
        this.swingWrappersFactory = swingWrappersFactory;
    }

    @Override
    public HasValue<String> nameInput() {
        return name;
    }

    @Override
    public HasClickHandlers greetButton() {
        return swingWrappersFactory.createJButtonHasClickHandler(button);
    }

    @Override
    public HasValue<String> messageDisplay() {
        return message;
    }

    @Override
    public HasClickHandlers closeButton() {
        return close;
    }

    @Override
    public void reveal() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void busy() {
    }

    @Override
    public void done() {
    }

    public JButton getButton() {
        return button;
    }
}