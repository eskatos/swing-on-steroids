/*
 * Copyright (c) 2009 Paul Merlin <paul@nosphere.org>
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.codeartisans.java.sos.views.handlers.ClickHandler;
import org.codeartisans.java.sos.views.notifications.ClickNotification;
import org.codeartisans.java.sos.views.values.ValueChangeHandler;
import org.codeartisans.java.sos.views.values.ValueChangeNotification;
import org.codeartisans.java.sos.wizard.graph.TransitionChange;
import org.codeartisans.java.sos.wizard.model.SignupWizardModel;
import org.codeartisans.java.sos.wizard.model.PlanType;
import org.codeartisans.java.sos.wizard.views.AccountPageView;
import org.codeartisans.java.sos.wizard.views.PlanTypeConfirmBlockingView;
import org.codeartisans.java.toolbox.StringUtils;

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
                    wizardModel.setPassword( StringUtils.EMPTY_CHAR_ARRAY );
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
                    wizardModel.setPassword( StringUtils.EMPTY_CHAR_ARRAY );
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
