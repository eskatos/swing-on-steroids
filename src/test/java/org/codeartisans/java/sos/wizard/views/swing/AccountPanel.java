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
package org.codeartisans.java.sos.wizard.views.swing;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;

import org.codeartisans.java.sos.wizard.model.PlanType;

public class AccountPanel
        extends JPanel
{

    private static final long serialVersionUID = 1L;
    private JTextField username = new JTextField();
    private JPasswordField password = new JPasswordField();
    private JPasswordField confirmation = new JPasswordField();
    private JComboBox planType = new JComboBox( new PlanType[]{ null,
                                                                PlanType.FREE,
                                                                PlanType.PAID } );

    public AccountPanel()
    {
        super( new MigLayout( new LC().fillX() ) );
        add( new JLabel( "Account informations" ), new CC().wrap() );
        add( new JLabel( "Username" ) );
        add( username, new CC().grow().wrap() );
        add( new JLabel( "Password" ) );
        add( password, new CC().grow().wrap() );
        add( new JLabel( "Confirm" ) );
        add( confirmation, new CC().grow().wrap() );
        add( new JLabel( "Plan type" ) );
        add( planType, new CC().grow() );
    }

    public JTextField username()
    {
        return username;
    }

    public JPasswordField password()
    {
        return password;
    }

    public JComboBox planType()
    {
        return planType;
    }

    public JPasswordField confirmation()
    {
        return confirmation;
    }

}
