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

import org.junit.Test;

/**
 * @author Paul Merlin 
 */
public class WizardTest
{

    @Test
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
