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
import org.codeartisans.java.sos.wizard.model.SignupWizardModel;
import org.codeartisans.java.sos.wizard.views.WelcomePageView;

public class WelcomePagePresenter
        extends WizardPagePresenter<SignupWizardModel, WelcomePageView>
{

    public static final WizardPageID PAGEID = new WizardPageID();

    public WelcomePagePresenter( WelcomePageView view )
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
        return "Welcome";
    }

    @Override
    public void onPageAdded( WizardPageHelper<SignupWizardModel> helper )
    {
    }

    @Override
    protected void onBind()
    {
    }

    @Override
    protected void onUnbind()
    {
    }

}
