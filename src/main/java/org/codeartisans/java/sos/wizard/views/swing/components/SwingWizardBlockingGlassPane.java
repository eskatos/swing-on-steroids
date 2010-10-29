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
package org.codeartisans.java.sos.wizard.views.swing.components;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JPanel;

import org.pushingpixels.trident.Timeline;
import org.pushingpixels.trident.Timeline.TimelineState;
import org.pushingpixels.trident.callback.TimelineCallbackAdapter;

/**
 * @author Paul Merlin
 * @author Fabien Barbero
 */
public class SwingWizardBlockingGlassPane
        extends JPanel
{

    private static final long serialVersionUID = 1L;
    private Component blockingComponent;

    public SwingWizardBlockingGlassPane()
    {
        super( null );
        super.setVisible( false );
        setOpaque( false );
        addMouseListener( new MouseAdapter()
        {
        } );
        addMouseMotionListener( new MouseMotionAdapter()
        {
        } );
    }

    @Override
    public void setVisible( boolean visible )
    {
        if ( isVisible() != visible ) {
            Dimension compSize = blockingComponent.getPreferredSize();
            int x = ( int ) ( getWidth() / 2 - compSize.getWidth() / 2 );
            Timeline timeline = new Timeline( blockingComponent );
            if ( !isVisible() ) {
                timeline.setDuration( 250 );
                timeline.addPropertyToInterpolate( "bounds",
                                                   new Rectangle( x, -compSize.height, compSize.width, compSize.height ),
                                                   new Rectangle( x, -1, compSize.width, compSize.height ) );
                super.setVisible( true );
                timeline.play();
            } else {
                timeline.setDuration( 100 );
                timeline.addPropertyToInterpolate( "bounds",
                                                   new Rectangle( x, -1, compSize.width, compSize.height ),
                                                   new Rectangle( x, -compSize.height, compSize.width, compSize.height ) );
                timeline.addCallback( new TimelineCallbackAdapter()
                {

                    @Override
                    public void onTimelineStateChanged( TimelineState oldState, TimelineState newState, float durationFraction, float timelinePosition )
                    {
                        if ( newState == TimelineState.DONE ) {
                            EventQueue.invokeLater( new Runnable()
                            {

                                @Override
                                public void run()
                                {
                                    SwingWizardBlockingGlassPane.super.setVisible( false );
                                }

                            } );
                        }
                    }

                } );
                timeline.play();
            }
        }
    }

    public void setBlockingComponent( Component blockingComponent )
    {
        if ( this.blockingComponent != null ) {
            remove( this.blockingComponent );
        }
        add( blockingComponent );
        this.blockingComponent = blockingComponent;
        validate();
        repaint();
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
