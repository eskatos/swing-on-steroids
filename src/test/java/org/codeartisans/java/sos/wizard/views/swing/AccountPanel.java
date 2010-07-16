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
