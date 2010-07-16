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
package org.codeartisans.java.sos.wizard.views.swing;

import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.layout.CC;
import net.miginfocom.swing.MigLayout;

import org.codeartisans.java.sos.wizard.model.PlanType;

/**
 * @author Paul Merlin
 */
public class PlanTypeConfirmBlockingPanel
        extends JPanel
{

    private static final long serialVersionUID = 1L;
    private JLabel msg = new JLabel();
    private JButton yes = new JButton( "Yes" );
    private JButton no = new JButton( "No" );

    public PlanTypeConfirmBlockingPanel()
    {
        super( new MigLayout() );
        add( msg, new CC().span( 2 ).alignX( "center" ).wrap() );
        add( yes );
        add( no );
        setOpaque( true );
        setBackground( Color.magenta );
    }

    public JButton yesButton()
    {
        return yes;
    }

    public JButton noButton()
    {
        return no;
    }

    public void setPlan( PlanType planType )
    {
        msg.setText( "Are you sure you want the " + planType + " plan?" );
    }

}
