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
