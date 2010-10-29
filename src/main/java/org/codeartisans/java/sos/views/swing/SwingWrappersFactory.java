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
package org.codeartisans.java.sos.views.swing;

import com.google.inject.Inject;

import java.awt.Window;
import java.awt.TrayIcon;
import java.awt.MenuItem;
import java.awt.Image;
import java.util.Collection;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JTree;

import org.codeartisans.java.sos.threading.WorkQueue;
import org.codeartisans.java.sos.views.handlers.HasButtonBehavior;
import org.codeartisans.java.sos.views.values.HasValueChangeHandlers;
import org.codeartisans.java.sos.views.handlers.HasClickHandlers;
import org.codeartisans.java.sos.views.handlers.HasFocusHandlers;
import org.codeartisans.java.sos.views.swing.components.EnhancedLabel;
import org.codeartisans.java.sos.views.swing.notifications.EnhancedLabelHasImageValueChangeHandlers;
import org.codeartisans.java.sos.views.swing.notifications.JButtonHasButtonBehavior;
import org.codeartisans.java.sos.views.swing.notifications.WindowHasFocusHandlers;
import org.codeartisans.java.sos.views.swing.notifications.JButtonHasClickHandlers;
import org.codeartisans.java.sos.views.swing.notifications.MenuItemHasClickHandlers;
import org.codeartisans.java.sos.views.swing.notifications.TrayIconHasClickHandlers;
import org.codeartisans.java.sos.views.swing.notifications.JFrameHasCloseClickHandlers;
import org.codeartisans.java.sos.views.swing.notifications.JComboBoxHasValueChangeHandlers;
import org.codeartisans.java.sos.views.swing.notifications.JPaneHasClickHandlers;
import org.codeartisans.java.sos.views.swing.notifications.JPasswordFieldHasCharsValueChangeHandlers;
import org.codeartisans.java.sos.views.swing.notifications.JTextComponentHasStringValueChangeHandlers;
import org.codeartisans.java.sos.views.swing.notifications.JTreeHasValueChangeHandlers;

/**
 * @author Paul Merlin
 * @author Jean-Michel Tonneau
 * @author Fabien Barbero
 * @author David Emo
 */
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

    public HasClickHandlers<Void> createJButtonHasClickHandler( JButton button )
    {
        return new JButtonHasClickHandlers( workQueue, button );
    }

    public HasButtonBehavior createJButtonHasButtonBehavior( JButton button )
    {
        return new JButtonHasButtonBehavior( workQueue, button );
    }

    public HasClickHandlers<Void> createPanelHasClickHandlers( JPanel glassPane )
    {
        return new JPaneHasClickHandlers( workQueue, glassPane );
    }

    public HasClickHandlers<Void> createJFrameHasCloseClickHandlers( JFrame frame )
    {
        return new JFrameHasCloseClickHandlers( workQueue, frame );
    }

    public HasClickHandlers<Void> createTrayIconHasClickHandlers( TrayIcon trayIcon )
    {
        return new TrayIconHasClickHandlers( workQueue, trayIcon );
    }

    public HasClickHandlers<Void> createMenuItemHasClickHandlers( MenuItem menuItem )
    {
        return new MenuItemHasClickHandlers( workQueue, menuItem );
    }

    public <V> HasValueChangeHandlers<V> createJComboBoxHasValueChangeHandlers( JComboBox jComboBox )
    {
        return new JComboBoxHasValueChangeHandlers<V>( workQueue, jComboBox );
    }

    // FIXME : use generics to box type of collection elements
    public HasValueChangeHandlers<Collection<?>> createJTreeHasTreePathValueChangeHandlers( JTree tree )
    {
        return new JTreeHasValueChangeHandlers( workQueue, tree );
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
