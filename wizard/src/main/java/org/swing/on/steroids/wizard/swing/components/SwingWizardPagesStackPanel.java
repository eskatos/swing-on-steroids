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

import java.awt.Color;
import java.awt.Component;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

import net.miginfocom.layout.AC;
import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;

/**
 * @author Paul Merlin
 */
public class SwingWizardPagesStackPanel
        extends JPanel
{

    private static final long serialVersionUID = 1L;
    private Component currentPageComponent;

    public SwingWizardPagesStackPanel()
    {
        super( new MigLayout( new LC().fill(),
                              new AC(),
                              new AC() ) );
        setBorder( BorderFactory.createLineBorder( Color.white ) );
    }

    public void setPageComponent( Component pageComponent )
    {
        if ( currentPageComponent != null ) {
            remove( currentPageComponent );
        }
        add( pageComponent, new CC().grow() );
        validate();
        repaint();
        currentPageComponent = pageComponent;
    }

}
