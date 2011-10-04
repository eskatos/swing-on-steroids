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
package org.swing.on.steroids.wizard.presenters;

import com.google.inject.Inject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.codeartisans.java.toolbox.Strings;
import org.swing.on.steroids.views.handlers.ClickHandler;
import org.swing.on.steroids.views.notifications.ClickNotification;
import org.swing.on.steroids.views.values.ValueChangeHandler;
import org.swing.on.steroids.views.values.ValueChangeNotification;
import org.swing.on.steroids.wizard.model.PlanType;
import org.swing.on.steroids.wizard.model.SignupWizardModel;
import org.swing.on.steroids.wizard.model.WizardPageID;
import org.swing.on.steroids.wizard.views.AccountPageView;
import org.swing.on.steroids.wizard.views.PlanTypeConfirmBlockingView;

/**
 * @author Paul Merlin
 */
public class AccountPagePresenter
        extends WizardPagePresenter<SignupWizardModel, AccountPageView>
{

    public static final WizardPageID PAGEID = new WizardPageID();
    private static final Iterable<TransitionChange> TO_FREE_PLAN;
    private static final Iterable<TransitionChange> TO_PAID_PLAN;

    static {
        List<TransitionChange> changes = new ArrayList<TransitionChange>();
        changes.add( new TransitionChange( AccountPagePresenter.PAGEID, CongratsPagePresenter.PAGEID, true ) );
        changes.add( new TransitionChange( AccountPagePresenter.PAGEID, CreditCardPagePresenter.PAGEID, false ) );
        changes.add( new TransitionChange( CreditCardPagePresenter.PAGEID, CongratsPagePresenter.PAGEID, false ) );
        TO_FREE_PLAN = changes;
        changes = new ArrayList<TransitionChange>();
        changes.add( new TransitionChange( AccountPagePresenter.PAGEID, CongratsPagePresenter.PAGEID, false ) );
        changes.add( new TransitionChange( AccountPagePresenter.PAGEID, CreditCardPagePresenter.PAGEID, true ) );
        changes.add( new TransitionChange( CreditCardPagePresenter.PAGEID, CongratsPagePresenter.PAGEID, true ) );
        TO_PAID_PLAN = changes;
    }

    private WizardPresenter<SignupWizardModel> wizardPresenter;
    private SignupWizardModel wizardModel;
    private final PlanTypeConfirmBlockingView planTypeBlockingView;

    @Inject
    public AccountPagePresenter( AccountPageView view, PlanTypeConfirmBlockingView planTypeBlockingView )
    {
        super( view );
        this.planTypeBlockingView = planTypeBlockingView;
    }

    @Override
    public WizardPageID wizardPageID()
    {
        return PAGEID;
    }

    @Override
    public String title()
    {
        return "Account";
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
        wizardPresenter.setButtonEnabled( WizardButton.next, wizardModel.isAccountComplete() );
        if ( wizardModel.getPlan() != null ) {
            switch ( wizardModel.getPlan() ) {
                case FREE:
                    wizardPresenter.applyTransitionChanges( TO_FREE_PLAN );
                    break;
                case PAID:
                    wizardPresenter.applyTransitionChanges( TO_PAID_PLAN );
                    break;
            }
        }
    }

    @Override
    protected void onBind()
    {
        recordViewRegistration( view.username().addValueChangeHandler( new ValueChangeHandler<String>()
        {

            @Override
            public void onValueChange( ValueChangeNotification<String> notification )
            {
                wizardModel.setUsername( notification.getNewValue() );
                handleButtons();
            }

        } ) );
        recordViewRegistration( view.password().addValueChangeHandler( new ValueChangeHandler<char[]>()
        {

            @Override
            public void onValueChange( ValueChangeNotification<char[]> notification )
            {
                if ( Arrays.equals( notification.getNewValue(), view.confirmation().getValue() ) ) {
                    wizardModel.setPassword( notification.getNewValue() );
                } else {
                    wizardModel.setPassword( Strings.EMPTY_CHAR_ARRAY );
                }
                handleButtons();
            }

        } ) );
        recordViewRegistration( view.confirmation().addValueChangeHandler( new ValueChangeHandler<char[]>()
        {

            @Override
            public void onValueChange( ValueChangeNotification<char[]> notification )
            {
                if ( Arrays.equals( notification.getNewValue(), view.password().getValue() ) ) {
                    wizardModel.setPassword( notification.getNewValue() );
                } else {
                    wizardModel.setPassword( Strings.EMPTY_CHAR_ARRAY );
                }
                handleButtons();
            }

        } ) );
        recordViewRegistration( view.planType().addValueChangeHandler( new ValueChangeHandler<PlanType>()
        {

            @Override
            public void onValueChange( ValueChangeNotification<PlanType> notification )
            {
                if ( wizardModel.getPlan() == null ) {
                    if ( notification.getNewValue() != null ) {
                        planTypeBlockingView.setPlan( notification.getNewValue() );
                        blockedWizard = wizardPresenter.blockWizard( planTypeBlockingView );
                    }
                } else {
                    if ( notification.getNewValue() == null ) {
                        wizardModel.setPlan( null );
                        handleButtons();
                    } else if ( !notification.getNewValue().equals( wizardModel.getPlan() ) ) {
                        planTypeBlockingView.setPlan( notification.getNewValue() );
                        blockedWizard = wizardPresenter.blockWizard( planTypeBlockingView );
                    }
                }
            }

        } ) );
        recordViewRegistration( planTypeBlockingView.yesButton().addClickHandler( new ClickHandler<Void>()
        {

            @Override
            public void onClick( ClickNotification<Void> notification )
            {
                blockedWizard.unblockWizard();
                wizardModel.setPlan( view.planType().getValue() );
                handleButtons();
            }

        } ) );
        recordViewRegistration( planTypeBlockingView.noButton().addClickHandler( new ClickHandler<Void>()
        {

            @Override
            public void onClick( ClickNotification<Void> notification )
            {
                blockedWizard.unblockWizard();
                view.planType().setValue( wizardModel.getPlan() );
                handleButtons();
            }

        } ) );
    }

    private WizardBlockingRegistration blockedWizard;

    @Override
    protected void onUnbind()
    {
    }

}
