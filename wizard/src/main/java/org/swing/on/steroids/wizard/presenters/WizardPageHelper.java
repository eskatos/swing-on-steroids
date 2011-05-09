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

import org.swing.on.steroids.wizard.model.WizardModel;

/**
 * A WizardPageHelper is an object that is injected into a {@link WizardPagePresenter} (via
 * {@link WizardPagePresenter#onPageAdded(WizardPageHelper)}) to allow the page to access {@link WizardPresenter}
 * specific data, including its {@link WizardModel}. A WizardPageHelper should never need to be instantiated in your
 * code.
 *
 * @param <M> the {@link WizardModel} type
 *
 * @author Paul Merlin
 */
public class WizardPageHelper<M extends WizardModel>
{

    private final WizardPresenter<M> wizardPresenter;

    /* package */ WizardPageHelper( WizardPresenter<M> wizardPresenter )
    {
        this.wizardPresenter = wizardPresenter;
    }

    public WizardPresenter<M> wizardPresenter()
    {
        return this.wizardPresenter;
    }

}
