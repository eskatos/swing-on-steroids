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
package org.codeartisans.java.sos.wizard.presenters;

import org.codeartisans.java.sos.wizard.model.WizardPageID;
import org.codeartisans.java.sos.views.values.ValueChangeHandler;
import org.codeartisans.java.sos.views.values.ValueChangeNotification;
import org.codeartisans.java.sos.wizard.model.CreditCardType;
import org.codeartisans.java.sos.wizard.model.SignupWizardModel;
import org.codeartisans.java.sos.wizard.views.CreditCardPageView;

public class CreditCardPagePresenter
        extends WizardPagePresenter<SignupWizardModel, CreditCardPageView>
{

    public static final WizardPageID PAGEID = new WizardPageID();
    private WizardPresenter<SignupWizardModel> wizardPresenter;
    private SignupWizardModel wizardModel;

    public CreditCardPagePresenter( CreditCardPageView view )
    {
        super( view );
    }

    @Override
    public WizardPageID wizardPageID()
    {
        return PAGEID;
    }

    @Override
    public String title()
    {
        return "Credit Card";
    }

    @Override
    public void onPageAdded( WizardPageHelper<SignupWizardModel> helper )
    {
        wizardModel = helper.wizardPresenter().wizardModel();
        wizardPresenter = helper.wizardPresenter();
    }

    @Override
    public void beforeShow()
    {
        wizardPresenter.disableAutomaticButtonsDuringNextTransition();
        handleButtons();
    }

    private void handleButtons()
    {
        wizardPresenter.setButtonEnabled( WizardButton.previous, true );
        wizardPresenter.setButtonEnabled( WizardButton.next, wizardModel.isCreditCardComplete() );
    }

    @Override
    protected void onBind()
    {
        recordViewRegistration( view.cardType().addValueChangeHandler( new ValueChangeHandler<CreditCardType>()
        {

            @Override
            public void onValueChange( ValueChangeNotification<CreditCardType> notification )
            {

                wizardModel.setCreditCardType( notification.getNewValue() );
                handleButtons();
            }

        } ) );
        recordViewRegistration( view.cardNumber().addValueChangeHandler( new ValueChangeHandler<String>()
        {

            @Override
            public void onValueChange( ValueChangeNotification<String> notification )
            {
                wizardModel.setCreditCardNumber( notification.getNewValue() );
                handleButtons();
            }

        } ) );
    }

    @Override
    protected void onUnbind()
    {
    }

}
