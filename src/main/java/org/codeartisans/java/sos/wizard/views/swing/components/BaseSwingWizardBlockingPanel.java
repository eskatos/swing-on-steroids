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

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.geom.Area;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JPanel;
import org.codeartisans.java.sos.views.swing.helpers.SwingHelper;

/**
 * @author Paul Merlin
 * @author Fabien Barbero
 */
public abstract class BaseSwingWizardBlockingPanel
        extends JPanel
{

    public BaseSwingWizardBlockingPanel( LayoutManager layoutManager )
    {
        super( layoutManager );
        setOpaque( false );
    }

    @Override
    protected void paintComponent( Graphics g )
    {
        super.paintComponent( g );
        Graphics2D g2d = SwingHelper.addAntiAliasing( g );
        Area shape = new Area( new RoundRectangle2D.Double( 0, 0, getWidth() - 1, getHeight() - 1, 20, 20 ) );
        shape.add( new Area( new Rectangle( 0, 0, getWidth() - 1, 20 ) ) );
        g2d.setColor( getBackground() );
        g2d.fill( shape );
        g2d.setColor( getForeground() );
        g2d.draw( shape );
    }

}
