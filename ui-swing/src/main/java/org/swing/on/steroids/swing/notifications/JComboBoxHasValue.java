/*
 * Copyright (c) 2010, Jean-Michel Tonneau. All Rights Reserved.
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

import javax.swing.JComboBox;
import org.swing.on.steroids.swing.helpers.SwingHelper;

import org.swing.on.steroids.views.values.HasValue;

/**
 * @param <V> Value type
 * @author jean-Michel Tonneau <barsifedron@gmail.com>
 */
public class JComboBoxHasValue<V>
        implements HasValue<V>
{

    protected final JComboBox jComboBox;

    public JComboBoxHasValue( JComboBox jComboBox )
    {
        this.jComboBox = jComboBox;
    }

    public final JComboBox getJComboBox()
    {
        return jComboBox;
    }

    @Override
    public final V getValue()
    {
        return ( V ) jComboBox.getSelectedItem();
    }

    @Override
    public final void setValue( final V value )
    {
        SwingHelper.invokeAndWait( new Runnable()
        {

            @Override
            public void run()
            {
                jComboBox.setSelectedItem( value );
            }

        } );
    }

}
