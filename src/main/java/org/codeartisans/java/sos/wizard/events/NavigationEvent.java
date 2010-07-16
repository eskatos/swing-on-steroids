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
package org.codeartisans.java.sos.wizard.events;

import org.codeartisans.java.sos.wizard.presenters.WizardPageID;

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
