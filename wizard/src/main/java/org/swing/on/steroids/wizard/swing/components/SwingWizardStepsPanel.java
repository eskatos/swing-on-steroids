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
package org.swing.on.steroids.wizard.swing.components;

import java.awt.Color;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.layout.AC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;

/**
 * @author Paul Merlin
 */
public class SwingWizardStepsPanel
        extends JPanel
{

    private static final long serialVersionUID = 1L;
    private final JLabel arrow;
    private int count = 0;
    private Color foreground;

    public SwingWizardStepsPanel()
    {
        super( new MigLayout( new LC(),
                              new AC(),
                              new AC() ) );
        setBorder( BorderFactory.createLineBorder( Color.white ) );
        try {
            arrow = new JLabel( new ImageIcon( ImageIO.read( SwingWizardStepsPanel.class.getResourceAsStream( "current.png" ) ) ) );
        } catch ( IOException ex ) {
            throw new RuntimeException( ex.getMessage(), ex );
        }
    }

    public void clearSteps()
    {
        removeAll();
        count = 0;
        validate();
    }

    @Override
    public void setForeground(Color foreground) {
        super.setForeground( foreground );
        this.foreground = foreground;
    }

    public void addStep( String title )
    {
        JLabel label = new JLabel( title );
        if(foreground != null) {
            label.setForeground( foreground );
        }
        add( label, "cell 1 " + count );
        count++;
    }

    public void setCurrent( int step )
    {
        remove( arrow );
        add( arrow, "cell 0 " + step );
        validate();
        repaint();
    }

}
