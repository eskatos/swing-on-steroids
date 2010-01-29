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
package org.codeartisans.java.sos.views.swing.notifications;

import javax.swing.JPasswordField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import org.codeartisans.java.sos.threading.WorkQueue;
import org.codeartisans.java.sos.views.values.ValueChangeHandler;
import org.codeartisans.java.sos.views.values.HasValueChangeHandlers;
import org.codeartisans.java.sos.views.values.ValueChangeNotification;
import org.codeartisans.java.sos.views.notifications.HandlerRegistration;

public final class JPasswordFieldHasCharsValueChangeHandlers
        extends JPasswordFieldHasCharsValue
        implements HasValueChangeHandlers<char[]>
{

    private final WorkQueue workQueue;

    public JPasswordFieldHasCharsValueChangeHandlers(WorkQueue workQueue, JPasswordField passwordField)
    {
        super(passwordField);
        this.workQueue = workQueue;
    }

    @Override
    public HandlerRegistration addValueChangeHandler(final ValueChangeHandler<char[]> handler)
    {
        final DocumentListener docListener = new DocumentListener()
        {

            @Override
            public void insertUpdate(DocumentEvent e)
            {
                onValueChange();
            }

            @Override
            public void removeUpdate(DocumentEvent e)
            {
                onValueChange();
            }

            @Override
            public void changedUpdate(DocumentEvent e)
            {
                onValueChange();
            }

            private void onValueChange()
            {
                workQueue.enqueue(new Runnable()
                {

                    @Override
                    public void run()
                    {
                        handler.onValueChange(new ValueChangeNotification<char[]>(passwordField.getPassword()));
                    }
                });
            }
        };
        passwordField.getDocument().addDocumentListener(docListener);
        return new HandlerRegistration()
        {

            @Override
            public void removeHandler()
            {
                passwordField.getDocument().removeDocumentListener(docListener);
            }
        };
    }
}
