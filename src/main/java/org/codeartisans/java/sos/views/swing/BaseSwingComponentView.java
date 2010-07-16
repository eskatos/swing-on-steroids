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
package org.codeartisans.java.sos.views.swing;

import java.awt.Component;
import java.awt.EventQueue;

import org.codeartisans.java.sos.views.View;

/**
 * A base {@link View} implementation for an AWT {@link Component}.
 *
 * Only {@link View#reveal()} and {@link View#hide()} fullfill their contracts.
 * Both {@link View#busy()} and {@link View#done()} are empty implementations meant to be overriden if needed.
 *
 * @author Paul Merlin
 */
public abstract class BaseSwingComponentView
        implements View
{

    /**
     * @return The AWT {@link Component} backing this {@link View}.
     */
    protected abstract Component delegate();

    @Override
    public void busy()
    {
    }

    @Override
    public void done()
    {
    }

    @Override
    public void reveal()
    {
        EventQueue.invokeLater( new Runnable()
        {

            @Override
            public void run()
            {
                delegate().setVisible( true );
            }

        } );
    }

    @Override
    public void hide()
    {
        EventQueue.invokeLater( new Runnable()
        {

            @Override
            public void run()
            {
                delegate().setVisible( false );
            }

        } );
    }

}
