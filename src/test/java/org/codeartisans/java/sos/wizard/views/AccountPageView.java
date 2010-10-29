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
import org.codeartisans.java.sos.wizard.model.PlanType;
import org.codeartisans.java.sos.wizard.views.swing.AccountPanel;

public class AccountPageView
        extends BaseSwingComponentSlotableView
        implements WizardPageView
{

    private AccountPanel delegate;
    private HasValueChangeHandlers<String> username;
    private HasValueChangeHandlers<char[]> password;
    private HasValueChangeHandlers<char[]> confirmation;
    private HasValueChangeHandlers<PlanType> planType;

    @Inject
    public AccountPageView( final SwingWrappersFactory swf )
    {
        SwingHelper.invokeAndWait( new Runnable()
        {

            @Override
            public void run()
            {
                delegate = new AccountPanel();
                username = swf.createJTextComponentHasStringValueChangeHandlers( delegate.username() );
                password = swf.createJPasswordFieldHasCharsValueChangeHandlers( delegate.password() );
                confirmation = swf.createJPasswordFieldHasCharsValueChangeHandlers( delegate.confirmation() );
                planType = swf.<PlanType>createJComboBoxHasValueChangeHandlers( delegate.planType() );
            }

        } );
    }

    @Override
    protected Component delegate()
    {
        return delegate;
    }

    public HasValueChangeHandlers<String> username()
    {
        return username;
    }

    public HasValueChangeHandlers<char[]> password()
    {
        return password;
    }

    public HasValueChangeHandlers<char[]> confirmation()
    {
        return confirmation;
    }

    public HasValueChangeHandlers<PlanType> planType()
    {
        return planType;
    }

}
