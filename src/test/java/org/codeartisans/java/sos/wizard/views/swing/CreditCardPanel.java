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
