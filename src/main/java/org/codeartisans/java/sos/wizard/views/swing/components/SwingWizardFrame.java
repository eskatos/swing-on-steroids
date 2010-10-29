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
