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
package org.codeartisans.java.sos.wizard.views.swing.components;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JPanel;

import net.miginfocom.layout.AC;
import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;

/**
 * @author Paul Merlin
 */
public class SwingWizardBlockingGlassPane
        extends JPanel
{

    private static final long serialVersionUID = 1L;
    private Component blockingComponent;

    public SwingWizardBlockingGlassPane()
    {
        super( new MigLayout( new LC().fill().insets( "0" ),
                              new AC(),
                              new AC() ) );
        setVisible( false );
        setOpaque( false );
        addMouseListener( new MouseAdapter()
        {
        } );
        addMouseMotionListener( new MouseMotionAdapter()
        {
        } );
    }

    public void setBlockingComponent( Component blockingComponent )
    {
        if ( this.blockingComponent != null ) {
            remove( this.blockingComponent );
        }
        Dimension compSize = blockingComponent.getPreferredSize();
        double x = getWidth() / 2 - compSize.getWidth() / 2;
        add( blockingComponent, new CC().pos( String.valueOf( x ), "0" ) );
        validate();
        repaint();
        this.blockingComponent = blockingComponent;
    }

    @Override
    protected void paintComponent( Graphics g )
    {
        super.paintComponent( g );
        Graphics2D g2d = ( Graphics2D ) g;
        g2d.setColor( Color.black );
        g2d.setComposite( AlphaComposite.getInstance( AlphaComposite.SRC_OVER, 0.5f ) );
        g2d.fillRect( 0, 0, this.getWidth(), this.getHeight() );
        g2d.setComposite( AlphaComposite.getInstance( AlphaComposite.SRC_OVER, 1f ) );
    }

}
