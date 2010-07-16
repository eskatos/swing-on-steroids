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

import java.util.HashSet;
import java.util.Set;

import org.codeartisans.java.sos.presenters.BasePresenter;
import org.codeartisans.java.sos.views.handlers.ClickHandler;
import org.codeartisans.java.sos.views.handlers.HasButtonBehavior;
import org.codeartisans.java.sos.views.notifications.ClickNotification;
import org.codeartisans.java.sos.wizard.model.WizardModel;
import org.codeartisans.java.sos.wizard.views.WizardBlockingView;
import org.codeartisans.java.sos.wizard.views.WizardPageView;
import org.codeartisans.java.sos.wizard.views.WizardView;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Paul Merlin 
 */
@SuppressWarnings( "ProtectedField" )
abstract class BaseWizardPresenter<M extends WizardModel>
        extends BasePresenter<WizardView<M>>
        implements WizardPresenter<M>
{

    private static final Logger LOGGER = LoggerFactory.getLogger( BaseWizardPresenter.class );
    private final Set<WizardPageID> shownPages = new HashSet<WizardPageID>();
    protected final WizardPageHelper<M> wizardHelper;
    protected M model;
    protected boolean automaticButtonsDisabled = false;

    public BaseWizardPresenter( String wizardTitle, M wizardModel, WizardView<M> wizardView )
    {
        super( wizardView );
        this.view.title().setValue( wizardTitle );
        this.model = wizardModel;
        this.wizardHelper = new WizardPageHelper<M>( this );
    }

    protected abstract WizardPageID previousPageID();

    protected abstract WizardPageID nextPageID();

    protected abstract void moveToWizardPage( WizardPageID pageID, boolean fireBeforeNext, boolean fireAfterNext, boolean fireBeforeShow );

    @Override
    public final M wizardModel()
    {
        return model;
    }

    @Override
    public final WizardBlockingRegistration blockWizard( WizardBlockingView blockingView )
    {
        view.showBlockingView( blockingView );
        return new WizardBlockingRegistration()
        {

            @Override
            public void unblockWizard()
            {
                view.removeBlockingView();
            }

        };
    }

    @Override
    public final void setButtonVisible( WizardButton button, boolean visible )
    {
        getButton( button ).setVisible( visible );
    }

    @Override
    public final void setButtonEnabled( WizardButton button, boolean enabled )
    {
        getButton( button ).setEnabled( enabled );
    }

    @Override
    public final void clickButton( WizardButton button )
    {
        getButton( button ).click();
    }

    /**
     * This method enables or disables the previous and next buttons based on the current page position and
     * whether there are any pages before or after the current page. The user can request that this functionality
     * be disabled for the <em>next page change</em> by calling {@link #disableAutomaticButtonsDuringNextTransition()}.
     */
    protected final void autoAdjustPreviousNextButtonStates()
    {
        // if the user has requested that they be allowed to override button states this transition,
        // return, but take control back next time.
        if ( automaticButtonsDisabled ) {
            automaticButtonsDisabled = false;
            return;
        }
        if ( previousPageID() == null ) {
            view.previousButton().setEnabled( false );
        } else {
            view.previousButton().setEnabled( true );
        }
        if ( nextPageID() == null ) {
            view.nextButton().setEnabled( false );
        } else {
            view.nextButton().setEnabled( true );
        }
    }

    protected final HasButtonBehavior getButton( WizardButton button )
    {
        switch ( button ) {
            case cancel:
                return view.cancelButton();
            case finish:
                return view.finishButton();
            case next:
                return view.nextButton();
            case previous:
                return view.previousButton();
            default:
                throw new InternalError( "You shall not pass!" );
        }
    }

    @Override
    public final void disableAutomaticButtonsDuringNextTransition()
    {
        automaticButtonsDisabled = true;
    }

    /**
     * If the target page was never shown before, call beforeFirstShow() and addPageView it to the list of previously
     * shown pages so we don't call it a second time.
     * 
     * @param pagePresenter Presenter of the WizardPage about to be shown
     */
    protected final void fireBeforeFirstShowIfNeeded( WizardPagePresenter<M, ? extends WizardPageView> pagePresenter )
    {
        if ( !shownPages.contains( pagePresenter.wizardPageID() ) ) {
            pagePresenter.bind();
            pagePresenter.beforeFirstShow();
            shownPages.add( pagePresenter.wizardPageID() );
        }
    }

    @Override
    protected final void onBind()
    {
        beforeBind();
        recordViewRegistration( view.nextButton().addClickHandler( new ClickHandler<Void>()
        {

            @Override
            public void onClick( ClickNotification<Void> notification )
            {
                LOGGER.trace( "next clicked" );
                moveToWizardPage( nextPageID(), true, true, true );
            }

        } ) );
        recordViewRegistration( view.previousButton().addClickHandler( new ClickHandler<Void>()
        {

            @Override
            public void onClick( ClickNotification<Void> notification )
            {
                LOGGER.trace( "previous clicked" );
                moveToWizardPage( previousPageID(), false, false, true );
            }

        } ) );
        afterBind();
    }

    protected void beforeBind()
    {
    }

    protected void afterBind()
    {
    }

    @Override
    protected final void onUnbind()
    {
        beforeUnbind();
        // NOOP ATM
        afterUnbind();
    }

    protected void beforeUnbind()
    {
    }

    protected void afterUnbind()
    {
    }

}
