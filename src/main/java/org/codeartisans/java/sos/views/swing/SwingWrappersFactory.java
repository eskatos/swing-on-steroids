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
import java.awt.TrayIcon;
import java.awt.MenuItem;
import java.awt.Image;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import org.codeartisans.java.sos.threading.WorkQueue;
import org.codeartisans.java.sos.views.values.HasValueChangeHandlers;
import org.codeartisans.java.sos.views.notifications.HasClickHandlers;
import org.codeartisans.java.sos.views.notifications.HasFocusHandlers;
import org.codeartisans.java.sos.views.swing.components.EnhancedLabel;
import org.codeartisans.java.sos.views.swing.notifications.EnhancedLabelHasImageValueChangeHandlers;
import org.codeartisans.java.sos.views.swing.notifications.WindowHasFocusHandlers;
import org.codeartisans.java.sos.views.swing.notifications.JButtonHasClickHandlers;
import org.codeartisans.java.sos.views.swing.notifications.MenuItemHasClickHandlers;
import org.codeartisans.java.sos.views.swing.notifications.TrayIconHasClickHandlers;
import org.codeartisans.java.sos.views.swing.notifications.JFrameHasCloseClickHandlers;
import org.codeartisans.java.sos.views.swing.notifications.JComboBoxHasValueChangeHandlers;
import org.codeartisans.java.sos.views.swing.notifications.JPaneHasClickHandlers;
import org.codeartisans.java.sos.views.swing.notifications.JPasswordFieldHasCharsValueChangeHandlers;
import org.codeartisans.java.sos.views.swing.notifications.JTextComponentHasStringValueChangeHandlers;

public final class SwingWrappersFactory
{

    private final WorkQueue workQueue;

    @Inject
    public SwingWrappersFactory( WorkQueue workQueue )
    {
        this.workQueue = workQueue;
    }

    public HasFocusHandlers createWindowHasFocusHandlers( Window window )
    {
        return new WindowHasFocusHandlers( workQueue, window );
    }

    public HasClickHandlers createJButtonHasClickHandler( JButton button )
    {
        return new JButtonHasClickHandlers( workQueue, button );
    }

    public HasClickHandlers createPanelHasClickHandlers( JPanel glassPane )
    {
        return new JPaneHasClickHandlers( workQueue, glassPane );
    }

    public HasClickHandlers createJFrameHasCloseClickHandlers( JFrame frame )
    {
        return new JFrameHasCloseClickHandlers( workQueue, frame );
    }

    public HasClickHandlers createTrayIconHasClickHandlers( TrayIcon trayIcon )
    {
        return new TrayIconHasClickHandlers( workQueue, trayIcon );
    }

    public HasClickHandlers createMenuItemHasClickHandlers( MenuItem menuItem )
    {
        return new MenuItemHasClickHandlers( workQueue, menuItem );
    }

    public HasValueChangeHandlers createJComboBoxHasValueChangeHandlers( JComboBox jComboBox )
    {
        return new JComboBoxHasValueChangeHandlers( workQueue, jComboBox );
    }

    public HasValueChangeHandlers<String> createJTextComponentHasStringValueChangeHandlers( JTextField textField )
    {
        return new JTextComponentHasStringValueChangeHandlers( workQueue, textField );
    }

    public HasValueChangeHandlers<char[]> createJPasswordFieldHasCharsValueChangeHandlers( JPasswordField passwordField )
    {
        return new JPasswordFieldHasCharsValueChangeHandlers( workQueue, passwordField );
    }

    public HasValueChangeHandlers<Image> createEnhancedLabelHasImageValueChangeHandlers( EnhancedLabel enhancedLabel )
    {
        return new EnhancedLabelHasImageValueChangeHandlers( workQueue, enhancedLabel );
    }

}
