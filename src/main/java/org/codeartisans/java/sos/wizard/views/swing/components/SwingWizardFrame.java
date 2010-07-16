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

import java.awt.Component;
import javax.swing.JFrame;

import net.miginfocom.layout.AC;
import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;

/**
 * @author Paul Merlin
 */
public class SwingWizardFrame
        extends JFrame
{

    private static final long serialVersionUID = 1L;
    private Component blockingGlasspane;
    private Component steps;
    private Component buttonBar;
    private Component pageStack;

    public SwingWizardFrame()
    {
        super();
        setSize( 800, 480 );
        setLayout( new MigLayout( new LC().fill(),
                                  new AC().align( "left" ),
                                  new AC().align( "top" ) ) );
    }

    public void setStepsComponent( Component steps )
    {
        if ( this.steps != null ) {
            remove( this.steps );
        }
        getContentPane().add( steps, new CC().width( "180!" ).dockWest() );
        this.steps = steps;
    }

    public void setButtonBarComponent( Component buttonBar )
    {
        if ( this.buttonBar != null ) {
            remove( this.buttonBar );
        }
        getContentPane().add( buttonBar, new CC().height( "40px!" ).dockSouth() );
        this.buttonBar = buttonBar;
    }

    public void setPagesStackComponent( Component pagesStack )
    {
        if ( this.pageStack != null ) {
            remove( this.pageStack );
        }
        getContentPane().add( pagesStack, new CC().grow() );
        this.pageStack = pagesStack;
    }

}
