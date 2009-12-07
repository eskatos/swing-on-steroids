package org.codeartisans.java.sos.views.swing.components;

import java.awt.BorderLayout;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JWindow;

public final class SplashWindow
        extends JWindow
{

    public SplashWindow(URL splashURL)
    {
        JLabel label = new JLabel(new ImageIcon(splashURL));
        getContentPane().add(label, BorderLayout.CENTER);
        setSize(label.getPreferredSize());
    }
}
