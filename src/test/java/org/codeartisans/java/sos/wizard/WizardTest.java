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
package org.codeartisans.java.sos.wizard;

import org.codeartisans.java.sos.threading.DefaultWorkQueue;
import org.codeartisans.java.sos.threading.WorkQueue;
import org.codeartisans.java.sos.views.swing.SwingWrappersFactory;
import org.codeartisans.java.sos.views.swing.helpers.SwingHelper;
import org.codeartisans.java.sos.wizard.model.SignupWizardModel;
import org.codeartisans.java.sos.wizard.presenters.AccountPagePresenter;
import org.codeartisans.java.sos.wizard.presenters.CongratsPagePresenter;
import org.codeartisans.java.sos.wizard.presenters.CreditCardPagePresenter;
import org.codeartisans.java.sos.wizard.presenters.WelcomePagePresenter;
import org.codeartisans.java.sos.wizard.presenters.WizardButton;
import org.codeartisans.java.sos.wizard.presenters.SignupWizardPresenter;
import org.codeartisans.java.sos.wizard.presenters.WizardPresenter;
import org.codeartisans.java.sos.wizard.views.AccountPageView;
import org.codeartisans.java.sos.wizard.views.CongratsPageView;
import org.codeartisans.java.sos.wizard.views.CreditCardPageView;
import org.codeartisans.java.sos.wizard.views.PlanTypeConfirmBlockingView;
import org.codeartisans.java.sos.wizard.views.WelcomePageView;
import org.codeartisans.java.sos.wizard.views.WizardView;
import org.codeartisans.java.sos.wizard.views.swing.SwingWizardView;

import org.junit.Ignore;
import org.junit.Test;

/**
 * @author Paul Merlin 
 */
public class WizardTest
{

    @Test
    @Ignore
    public void testWithoutGuice()
            throws InterruptedException
    {
        SwingHelper.initSafeSwing();

        WorkQueue workQueue = new DefaultWorkQueue( "WizardQueue", 5 );
        SwingWrappersFactory swingWrappersFactory = new SwingWrappersFactory( workQueue );

        WizardView<SignupWizardModel> wizView = new SwingWizardView<SignupWizardModel>( swingWrappersFactory );

        WizardPresenter<SignupWizardModel> wizPresenter = new SignupWizardPresenter( new SignupWizardModel(), wizView );

        wizPresenter.setButtonEnabled( WizardButton.finish, false );

        WelcomePagePresenter welcome = new WelcomePagePresenter( new WelcomePageView() );
        AccountPagePresenter account = new AccountPagePresenter( new AccountPageView( swingWrappersFactory ), new PlanTypeConfirmBlockingView( swingWrappersFactory ) );
        CreditCardPagePresenter card = new CreditCardPagePresenter( new CreditCardPageView( swingWrappersFactory ) );
        CongratsPagePresenter congrats = new CongratsPagePresenter( new CongratsPageView() );

        wizPresenter.addTransition( welcome, account, Boolean.TRUE );
        wizPresenter.addTransition( account, congrats, Boolean.TRUE );
        wizPresenter.addTransition( account, card, Boolean.FALSE );
        wizPresenter.addTransition( card, congrats, Boolean.FALSE );

        wizPresenter.bind();
        wizPresenter.view().reveal();

        Thread.sleep( 1000000 );

        wizPresenter.view().hide();
    }

}
