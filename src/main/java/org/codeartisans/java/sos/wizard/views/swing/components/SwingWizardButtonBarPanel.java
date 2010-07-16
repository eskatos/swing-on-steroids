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
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

import net.miginfocom.layout.AC;
import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;

/**
 * @author Paul Merlin
 */
public class SwingWizardButtonBarPanel
        extends JPanel
{

    private static final long serialVersionUID = 1L;
    private final JButton cancel = new JButton( "Cancel" );
    private final JButton previous = new JButton( "Previous" );
    private final JButton next = new JButton( "Next" );
    private final JButton finish = new JButton( "Finish" );

    public SwingWizardButtonBarPanel()
    {
        super( new MigLayout( new LC().fill(),
                              new AC(),
                              new AC() ) );
        setBorder( BorderFactory.createLineBorder( Color.white ) );
        add( cancel, new CC().alignX( "left" ) );
        add( previous, new CC().split( 2 ).alignX( "right" ) );
        add( next, new CC().alignX( "right" ) );
        add( finish, new CC().alignX( "right" ) );
    }

    public JButton cancel()
    {
        return cancel;
    }

    public JButton previous()
    {
        return previous;
    }

    public JButton next()
    {
        return next;
    }

    public JButton finish()
    {
        return finish;
    }

}
