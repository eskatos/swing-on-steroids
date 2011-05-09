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

import org.swing.on.steroids.presenters.Presenter;
import org.swing.on.steroids.wizard.model.WizardModel;
import org.swing.on.steroids.wizard.views.WizardBlockingView;
import org.swing.on.steroids.wizard.views.WizardPageView;

/**
 * A presenter for Wizards.
 *
 * @param M the model type extending {@link WizardModel}, this is the M in MVP
 */
public interface WizardPresenter<M extends WizardModel>
        extends Presenter
{

    M wizardModel();

    void addTransition( WizardPagePresenter<M, ? extends WizardPageView> previous, WizardPagePresenter<M, ? extends WizardPageView> next, Boolean enabled );

    void applyTransitionChanges( Iterable<TransitionChange> changes );

    void clickButton( WizardButton button );

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

    void previous();

    void next();

}
