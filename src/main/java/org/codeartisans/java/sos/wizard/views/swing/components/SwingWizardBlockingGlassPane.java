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
