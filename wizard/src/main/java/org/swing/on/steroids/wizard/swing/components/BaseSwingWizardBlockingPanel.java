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

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.geom.Area;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JPanel;
import org.swing.on.steroids.swing.helpers.SwingHelper;

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
