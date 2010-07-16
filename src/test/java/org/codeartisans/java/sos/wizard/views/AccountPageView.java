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
