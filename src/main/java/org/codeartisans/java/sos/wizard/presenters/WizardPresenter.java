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

import org.codeartisans.java.sos.presenters.Presenter;
import org.codeartisans.java.sos.wizard.graph.TransitionChange;
import org.codeartisans.java.sos.wizard.model.WizardModel;
import org.codeartisans.java.sos.wizard.views.WizardBlockingView;
import org.codeartisans.java.sos.wizard.views.WizardPageView;

/**
 * A presenter for Wizards.
 *
 * @param M the model type extending {@link WizardModel}, this is the M in MVP
 *
 * @author Paul Merlin
 */
public interface WizardPresenter<M extends WizardModel>
        extends Presenter
{

    M wizardModel();

    void addTransition( WizardPagePresenter<M, ? extends WizardPageView> previous, WizardPagePresenter<M, ? extends WizardPageView> next, Boolean enabled );

    void applyTransitionChanges( Iterable<TransitionChange> changes );

    void clickButton( WizardButton button );

    void goToNextPage();

    void setButtonVisible( WizardButton button, boolean visible );

    void setButtonEnabled( WizardButton button, boolean enabled );

    /**
     * Disabling automatic buttons means that the Wizard will not attempt to automatically calculate
     * button states based on the current page's position after the next page change. This is used for
     * {@link WizardPagePresenter#beforeShow()} and other {@link WizardPagePresenter} callbacks that need to manually
     * modify the button state. The {@link WizardPagePresenter} must <strong>completely</strong> handle the buttons
     * states after setting this to <code>true</code>. This value is reset to <code>false</code> after each page change.
     */
    void disableAutomaticButtonsDuringNextTransition();

    WizardBlockingRegistration blockWizard( WizardBlockingView blockingView );

}
