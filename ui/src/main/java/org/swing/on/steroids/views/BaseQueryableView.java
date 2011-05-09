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
package org.swing.on.steroids.views;

/**
 * Base class for Views that want to be QueryableView giving the common code for state accessors.
 */
public abstract class BaseQueryableView
        implements QueryableView
{

    private boolean revealed;
    private boolean busy;

    @Override
    public final void reveal()
    {
        onReveal();
        revealed = true;
    }

    @Override
    public final void hide()
    {
        onHide();
        revealed = false;
    }

    @Override
    public final void busy()
    {
        onBusy();
        busy = true;
    }

    @Override
    public final void done()
    {
        onDone();
        busy = false;
    }

    @Override
    public final boolean isRevealed()
    {
        return revealed;
    }

    @Override
    public final boolean isBusy()
    {
        return busy;
    }

    /**
     * Called when the View has to be revealed. Subtypes must implement this.
     */
    protected abstract void onReveal();

    /**
     * Called when the View has to be hiden. Subtypes must implement this.
     */
    protected abstract void onHide();

    /**
     * Called when the View has to switch to busy state. Subtypes must implement this.
     */
    protected abstract void onBusy();

    /**
     * Called when the View has to switch back to normal state from busy state. Subtypes must implement this.
     */
    protected abstract void onDone();

}
