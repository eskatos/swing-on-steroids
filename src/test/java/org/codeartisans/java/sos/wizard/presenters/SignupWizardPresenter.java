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
package org.codeartisans.java.sos.wizard.presenters;

import com.google.inject.Inject;

import org.codeartisans.java.sos.views.handlers.ClickHandler;
import org.codeartisans.java.sos.views.notifications.ClickNotification;
import org.codeartisans.java.sos.wizard.model.SignupWizardModel;
import org.codeartisans.java.sos.wizard.views.WizardView;

/**
 * @author Paul Merlin
 */
public class SignupWizardPresenter
        extends DefaultWizardPresenter<SignupWizardModel>
{

    @Inject
    public SignupWizardPresenter( SignupWizardModel wizardModel, WizardView<SignupWizardModel> wizardView )
    {
        super( "Signup Wizard", wizardModel, wizardView );
    }

    @Override
    protected void afterBind()
    {
        recordViewRegistration( getButton( WizardButton.cancel ).addClickHandler( new ClickHandler<Void>()
        {

            @Override
            public void onClick( ClickNotification<Void> notification )
            {
                System.exit( 1 );
            }

        } ) );
        recordViewRegistration( getButton( WizardButton.finish ).addClickHandler( new ClickHandler<Void>()
        {

            @Override
            public void onClick( ClickNotification<Void> notification )
            {
                System.exit( 0 );
            }

        } ) );
        super.afterBind();
    }

}
