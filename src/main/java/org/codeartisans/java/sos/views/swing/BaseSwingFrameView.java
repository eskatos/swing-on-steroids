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
package org.codeartisans.java.sos.views.swing;

import java.awt.EventQueue;
import java.awt.Frame;

import org.codeartisans.java.sos.views.View;

/**
 * A base {@link View} implementation for an AWT {@link Frame}.
 * 
 * Only {@link View#reveal()} and {@link View#hide()} fullfill their contracts.
 * Both {@link View#busy()} and {@link View#done()} are empty implementations meant to be overriden if needed.
 *
 * @author Paul Merlin
 */
public abstract class BaseSwingFrameView
        implements View
{

    /**
     * @return The AWT {@link Frame} backing this {@link View}.
     */
    protected abstract Frame delegate();

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
                delegate().dispose();
            }

        } );
    }

}
