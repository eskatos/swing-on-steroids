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

import java.awt.Image;
import javax.swing.ImageIcon;

import org.swing.on.steroids.swing.components.EnhancedLabel;
import org.swing.on.steroids.views.values.HasValue;

public class EnhancedLabelHasImageValue
        implements HasValue<Image>
{

    protected final EnhancedLabel enhancedLabel;

    public EnhancedLabelHasImageValue( EnhancedLabel enhancedField )
    {
        this.enhancedLabel = enhancedField;
    }

    @Override
    public final Image getValue()
    {
        return enhancedLabel.getImage();
    }

    @Override
    public final void setValue( Image value )
    {
        enhancedLabel.setIcon( new ImageIcon( value ) );
    }

    public final EnhancedLabel getEnhancedLabel()
    {
        return enhancedLabel;
    }

}
