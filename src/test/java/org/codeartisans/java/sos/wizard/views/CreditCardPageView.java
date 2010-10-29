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
