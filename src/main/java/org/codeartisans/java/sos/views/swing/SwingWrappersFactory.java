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
package org.codeartisans.java.sos.views.swing;

import com.google.inject.Inject;
import java.awt.Window;
import javax.swing.JButton;
import javax.swing.JFrame;
import org.codeartisans.java.sos.threading.WorkQueue;
import org.codeartisans.java.sos.views.notifications.HasClickHandlers;
import org.codeartisans.java.sos.views.notifications.HasFocusHandlers;
import org.codeartisans.java.sos.views.swing.notifications.JButtonHasClickHandlers;
import org.codeartisans.java.sos.views.swing.notifications.JFrameHasCloseClickHandlers;
import org.codeartisans.java.sos.views.swing.notifications.WindowHasFocusHandlers;

public class SwingWrappersFactory {

    private final WorkQueue workQueue;

    @Inject
    public SwingWrappersFactory(WorkQueue workQueue) {
        this.workQueue = workQueue;
    }

    public HasClickHandlers createJButtonHasClickHandler(JButton button) {
        return new JButtonHasClickHandlers(workQueue, button);
    }

    public HasClickHandlers createJFrameHasCloseClickHandlers(JFrame frame) {
        return new JFrameHasCloseClickHandlers(workQueue, frame);
    }

    public HasFocusHandlers createWindowHasFocusHandlers(Window window) {
        return new WindowHasFocusHandlers(workQueue, window);
    }

}