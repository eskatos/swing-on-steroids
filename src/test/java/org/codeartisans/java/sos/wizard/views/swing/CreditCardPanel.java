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
import javax.swing.JTextField;

import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;

import org.codeartisans.java.sos.wizard.model.CreditCardType;

public class CreditCardPanel
        extends JPanel
{

    private static final long serialVersionUID = 1L;
    private JComboBox cardType = new JComboBox( new CreditCardType[]{ null,
                                                                      CreditCardType.AMERICAN_EXPRESS,
                                                                      CreditCardType.MASTERCARD,
                                                                      CreditCardType.VISA } );
    private JTextField cardNumber = new JTextField();

    public CreditCardPanel()
    {
        super( new MigLayout( new LC().fillX() ) );
        add( new JLabel( "Give me your number!" ), new CC().wrap() );
        add( new JLabel( "Credit card type" ) );
        add( cardType, new CC().grow().wrap() );
        add( new JLabel( "Credit card number" ) );
        add( cardNumber, new CC().grow() );
    }

    public JComboBox cardType()
    {
        return cardType;
    }

    public JTextField cardNumber()
    {
        return cardNumber;
    }

}
