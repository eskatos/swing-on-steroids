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
package org.codeartisans.java.sos.wizard.presenters;

import org.codeartisans.java.sos.presenters.BasePresenter;
import org.codeartisans.java.sos.wizard.events.NavigationEvent;
import org.codeartisans.java.sos.wizard.model.WizardModel;
import org.codeartisans.java.sos.wizard.model.WizardPageID;
import org.codeartisans.java.sos.wizard.views.WizardPageView;

/**
 * {@link WizardPagePresenter}s equality is based on their {@link WizardPageID}.
 *
 * @author Paul Merlin
 */
public abstract class WizardPagePresenter<M extends WizardModel, V extends WizardPageView>
        extends BasePresenter<V>
{

    public WizardPagePresenter( V view )
    {
        super( view );
    }

    public abstract WizardPageID wizardPageID();

    /**
     * Gets the title of the page to use in the navigation view.
     * Use <code>null</code> or an empty string to keep this page from showing up in the list of pages; any pages
     * with the same name will only be shown in the list once.
     * This is useful for pages that are the result of a branch in logic.
     * 
     * @return the title of the page
     */
    public abstract String title();

    /**
     * Called when the page is added to the Wizard, providing a {@link WizardPageHelper}.
     * 
     * @param helper the Wizard's {@link WizardPageHelper}
     */
    public abstract void onPageAdded( WizardPageHelper<M> helper );

    /**
     * Called before the first time a page is shown.
     * Allows for any setup the page may want to do.
     */
    public void beforeFirstShow()
    {
    }

    /**
     * Called just before each time the page is shown.
     * Allows the page to set up any elements based on the current {@link WizardModel}.
     * This function is called before the prior page's {@link #afterNext()}.
     */
    public void beforeShow()
    {
    }

    /**
     * Called when the "Next" button is clicked, but before the page is actually changed. 
     * Allows the page to do any validation, etc. 
     * Call {@link NavigationEvent#cancel()} to cancel the page change.
     */
    public void beforeNext( final NavigationEvent event )
    {
    }

    /**
     * Called after the page has disappeared due to the {@link WizardPresenter} moving to the next page.
     * Allows the page to do any UI cleanup without strange screen artifacts.
     * This function should not do any modification of the {@link WizardModel} that the next page will need during
     * its {@link #beforeShow()}, as {@link #beforeShow()} is called before <code>afterNext()</code>.
     */
    public void afterNext()
    {
    }

    @Override
    public final boolean equals( Object obj )
    {
        if ( obj == null ) {
            return false;
        }
        if ( getClass() != obj.getClass() ) {
            return false;
        }
        @SuppressWarnings( "unchecked" )
        final WizardPagePresenter<M, ?> other = ( WizardPagePresenter<M, ?> ) obj;
        WizardPageID selfID = wizardPageID();
        WizardPageID otherID = other.wizardPageID();
        if ( selfID != otherID && ( selfID == null || !selfID.equals( otherID ) ) ) {
            return false;
        }
        return true;
    }

    @Override
    public final int hashCode()
    {
        int hash = 3;
        WizardPageID selfID = wizardPageID();
        hash = 97 * hash + ( selfID != null ? selfID.hashCode() : 0 );
        return hash;
    }

}
