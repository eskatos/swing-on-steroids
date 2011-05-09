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
import org.swing.on.steroids.wizard.model.WizardPageID;
import org.swing.on.steroids.wizard.views.WizardPageView;

/**
 * @author Paul Merlin 
 */
public class PageVertex<M extends WizardModel>
{

    private final WizardPagePresenter<M, ? extends WizardPageView> wizardPagePresenter;

    public PageVertex( WizardPagePresenter<M, ? extends WizardPageView> wizardPagePresenter )
    {
        this.wizardPagePresenter = wizardPagePresenter;
    }

    public WizardPagePresenter<M, ? extends WizardPageView> presenter()
    {
        return wizardPagePresenter;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder( "PageVertex{" );
        sb.append( wizardPagePresenter.wizardPageID() );
        return sb.append( "}" ).toString();
    }

    public WizardPageID wizardPageID()
    {
        return wizardPagePresenter.wizardPageID();
    }

    @Override
    @SuppressWarnings( "AccessingNonPublicFieldOfAnotherObject" )
    public boolean equals( Object obj )
    {
        if ( obj == null ) {
            return false;
        }
        if ( getClass() != obj.getClass() ) {
            return false;
        }
        @SuppressWarnings( "unchecked" )
        final PageVertex<M> other = ( PageVertex<M> ) obj;
        if ( this.wizardPagePresenter != other.wizardPagePresenter && ( this.wizardPagePresenter == null || !this.wizardPagePresenter.equals( other.wizardPagePresenter ) ) ) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 59 * hash + ( this.wizardPagePresenter != null ? this.wizardPagePresenter.hashCode() : 0 );
        return hash;
    }

}
