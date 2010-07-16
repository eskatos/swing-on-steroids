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
package org.codeartisans.java.sos.wizard.views;

import com.google.inject.Inject;

import java.awt.Component;

import org.codeartisans.java.sos.views.swing.BaseSwingComponentSlotableView;
import org.codeartisans.java.sos.views.swing.SwingWrappersFactory;
import org.codeartisans.java.sos.views.swing.helpers.SwingHelper;
import org.codeartisans.java.sos.views.values.HasValueChangeHandlers;
import org.codeartisans.java.sos.wizard.model.CreditCardType;
import org.codeartisans.java.sos.wizard.views.swing.CreditCardPanel;

public class CreditCardPageView
        extends BaseSwingComponentSlotableView
        implements WizardPageView
{

    private CreditCardPanel delegate;
    private HasValueChangeHandlers<CreditCardType> cardType;
    private HasValueChangeHandlers<String> cardNumber;

    @Inject
    public CreditCardPageView( final SwingWrappersFactory swf )
    {
        SwingHelper.invokeAndWait( new Runnable()
        {

            @Override
            public void run()
            {
                delegate = new CreditCardPanel();
                cardType = swf.<CreditCardType>createJComboBoxHasValueChangeHandlers( delegate.cardType() );
                cardNumber = swf.createJTextComponentHasStringValueChangeHandlers( delegate.cardNumber() );
            }

        } );
    }

    @Override
    protected Component delegate()
    {
        return delegate;
    }

    public HasValueChangeHandlers<CreditCardType> cardType()
    {
        return cardType;
    }

    public HasValueChangeHandlers<String> cardNumber()
    {
        return cardNumber;
    }

}
