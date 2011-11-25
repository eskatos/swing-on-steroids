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
package org.swing.on.steroids.swing.notifications;


import javax.swing.AbstractButton;

import org.swing.on.steroids.threading.WorkQueue;
import org.swing.on.steroids.views.handlers.HasButtonBehavior;
import org.swing.on.steroids.swing.helpers.SwingHelper;

public class ButtonHasButtonBehavior
        extends ButtonHasClickHandlers
        implements HasButtonBehavior
{

    private static final long serialVersionUID = 1L;

    public ButtonHasButtonBehavior( WorkQueue workQueue, AbstractButton button )
    {
        super( workQueue, button );
    }

    @Override
    public void click()
    {
        SwingHelper.invokeAndWait( new Runnable()
        {

            @Override
            public void run()
            {
                getJButton().doClick();
            }

        } );
    }

    @Override
    public boolean isEnabled()
    {
        return getJButton().isEnabled();
    }

    @Override
    public void setEnabled( final boolean enabled )
    {
        SwingHelper.invokeAndWait( new Runnable()
        {

            @Override
            public void run()
            {
                getJButton().setEnabled( enabled );
            }

        } );
    }

    @Override
    public boolean isVisible()
    {
        return getJButton().isVisible();
    }

    @Override
    public void setVisible( final boolean visible )
    {
        SwingHelper.invokeAndWait( new Runnable()
        {

            @Override
            public void run()
            {
                getJButton().setVisible( visible );
            }

        } );
    }

}
