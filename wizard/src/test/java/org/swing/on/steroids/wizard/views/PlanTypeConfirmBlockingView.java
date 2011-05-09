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
package org.swing.on.steroids.wizard.views;

import org.swing.on.steroids.wizard.views.WizardBlockingView;
import com.google.inject.Inject;
import java.awt.Component;

import java.awt.EventQueue;

import org.swing.on.steroids.views.handlers.HasClickHandlers;
import org.swing.on.steroids.swing.BaseSwingComponentSlotableView;
import org.swing.on.steroids.swing.SwingWrappersFactory;
import org.swing.on.steroids.swing.helpers.SwingHelper;
import org.swing.on.steroids.wizard.model.PlanType;
import org.swing.on.steroids.wizard.views.swing.PlanTypeConfirmBlockingPanel;

/**
 * @author Paul Merlin
 */
public class PlanTypeConfirmBlockingView
        extends BaseSwingComponentSlotableView
        implements WizardBlockingView
{

    private PlanTypeConfirmBlockingPanel delegate;
    private HasClickHandlers<Void> yes;
    private HasClickHandlers<Void> no;

    @Inject
    public PlanTypeConfirmBlockingView( final SwingWrappersFactory swf )
    {
        SwingHelper.invokeAndWait( new Runnable()
        {

            @Override
            public void run()
            {
                delegate = new PlanTypeConfirmBlockingPanel();
                yes = swf.createJButtonHasClickHandler( delegate.yesButton() );
                no = swf.createJButtonHasClickHandler( delegate.noButton() );
            }

        } );
    }

    @Override
    protected Component delegate()
    {
        return delegate;
    }

    public HasClickHandlers<Void> yesButton()
    {
        return yes;
    }

    public HasClickHandlers<Void> noButton()
    {
        return no;
    }

    public void setPlan( final PlanType planType )
    {
        SwingHelper.invokeAndWait( new Runnable()
        {

            @Override
            public void run()
            {
                delegate.setPlan( planType );
            }

        } );
    }

}
