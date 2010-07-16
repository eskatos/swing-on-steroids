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

import org.codeartisans.java.sos.wizard.model.WizardModel;

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
