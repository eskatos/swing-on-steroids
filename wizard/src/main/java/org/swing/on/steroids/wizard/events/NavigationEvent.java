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
package org.swing.on.steroids.wizard.events;

import org.swing.on.steroids.wizard.model.WizardPageID;

/**
 * An event passed to the {@link WizardPagePresenter} callbacks.
 * 
 * {@link WizardPagePresenter#beforeNext(NavigationEvent)} can cancel the navigation by calling {@link #cancel()}, 
 * and can redirect to a new page by calling {@link NavigationEvent#setDestinationPage(WizardPageID)}.
 *
 * @author Paul Merlin
 */
public final class NavigationEvent
{

    private boolean alive;
    private boolean fireBeforeNext;
    private boolean fireAfterNext;
    private boolean fireBeforeShow;
    private WizardPageID sourcePageID;
    private WizardPageID destinationPageID;

    /**
     * Construct a new NavigationEvent with a reference to the parent {@link Wizard} and the source and destination {@link WizardPagePresenter}s.
     * 
     * @param sourcePageID the {@link WizardPageID} of the source {@link WizardPagePresenter}
     * @param destinationPageID the {@link WizardPageID} of the destination {@link WizardPagePresenter}
     */
    public NavigationEvent( WizardPageID sourcePageID, WizardPageID destinationPageID )
    {
        alive = true;
        setFireBeforeNext( true );
        setFireAfterNext( true );
        setFireBeforeShow( true );
        this.sourcePageID = sourcePageID;
        setDestinationPageID( destinationPageID );
    }

    /**
     * Cancels the navigation event if the event is cancelable.
     * The event is only cancelabe during {@link WizardPagePresenter#beforeNext(NavigationEvent)}.
     */
    public void cancel()
    {
        alive = false;
    }

    /**
     * Whether or not the event is still alive. {@link #cancel()} kills the event.
     * 
     * @return <code>true</code> if the event is alive, <code>false</code> otherwise
     */
    public boolean isAlive()
    {
        return alive;
    }

    /**
     * Returns the {@link PageID} of the source page.
     *
     * @return the {@link PageID} of the source page
     */
    public WizardPageID getSourcePageID()
    {
        return sourcePageID;
    }

    /**
     * Returns the {@link PageID} of the destination page.
     *
     * @return the {@link PageID} of the destination page
     */
    public WizardPageID getDestinationPageID()
    {
        return destinationPageID;
    }

    /**
     * Sets a new destination page. Has no effect if the event is not being processed in {@link WizardPagePresenter#beforeNext(NavigationEvent)}.
     * 
     * @param destinationPageID
     */
    public void setDestinationPageID( WizardPageID destinationPageID )
    {
        this.destinationPageID = destinationPageID;
    }

    /**
     * Gets whether or not to call the {@link WizardPagePresenter#beforeNext(NavigationEvent)} hook.
     *
     * @return <code>true</code> if the hook should be called, <code>false</code> otherwise
     */
    public boolean getFireBeforeNext()
    {
        return fireBeforeNext;
    }

    /**
     * Sets whether or not to call the {@link WizardPagePresenter#afterNext()} hook.
     *
     * @param fireBeforeNext <code>true</code> if the hook should be called, <code>false</code> otherwise
     */
    public void setFireBeforeNext( boolean fireBeforeNext )
    {
        this.fireBeforeNext = fireBeforeNext;
    }

    /**
     * Gets whether or not to call the {@link WizardPagePresenter#beforeNext(NavigationEvent)} hook.
     *
     * @return <code>true</code> if the hook should be called, <code>false</code> otherwise
     */
    public boolean getFireAfterNext()
    {
        return fireAfterNext;
    }

    /**
     * Sets whether or not to call the {@link WizardPagePresenter#afterNext()} hook.
     *
     * @param fireAfterNext <code>true</code> if the hook should be called, <code>false</code> otherwise
     */
    public void setFireAfterNext( boolean fireAfterNext )
    {
        this.fireAfterNext = fireAfterNext;
    }

    /**
     * Gets whether or not to call the {@link WizardPagePresenter#beforeShow()} hook.
     *
     * @return <code>true</code> if the hook should be called, <code>false</code> otherwise
     */
    public boolean getFireBeforeShow()
    {
        return fireBeforeShow;
    }

    /**
     * Sets whether or not to call the {@link WizardPagePresenter#beforeShow()} hook.
     * 
     * @param fireBeforeShow <code>true</code> if the hook should be called, <code>false</code> otherwise
     */
    public void setFireBeforeShow( boolean fireBeforeShow )
    {
        this.fireBeforeShow = fireBeforeShow;
    }

}
