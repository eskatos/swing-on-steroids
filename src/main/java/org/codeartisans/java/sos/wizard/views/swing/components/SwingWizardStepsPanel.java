/*
 * Copyright (c) 2010 Paul Merlin <paul@nosphere.org>
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
package org.codeartisans.java.sos.wizard.views.swing.components;

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

    public void addStep( String title )
    {
        add( new JLabel( title ), "cell 1 " + count );
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
