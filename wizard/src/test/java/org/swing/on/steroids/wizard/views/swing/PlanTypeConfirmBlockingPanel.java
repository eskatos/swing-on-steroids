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
package org.swing.on.steroids.wizard.views.swing;

import javax.swing.JButton;
import javax.swing.JLabel;

import net.miginfocom.layout.CC;
import net.miginfocom.swing.MigLayout;

import org.swing.on.steroids.wizard.model.PlanType;
import org.swing.on.steroids.wizard.swing.components.BaseSwingWizardBlockingPanel;

public class PlanTypeConfirmBlockingPanel
        extends BaseSwingWizardBlockingPanel
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
