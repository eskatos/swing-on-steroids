/*
 * Copyright (c) 2009 Paul Merlin <paul@nosphere.org>
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
package org.codeartisans.java.sos.views;

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
