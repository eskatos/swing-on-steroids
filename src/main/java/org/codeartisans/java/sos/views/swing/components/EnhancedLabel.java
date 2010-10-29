/*
 * Copyright (c) 2010, David Emo. All Rights Reserved.
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
package org.codeartisans.java.sos.views.swing.components;

import java.awt.Image;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * @author David Emo
 */
public class EnhancedLabel
        extends JLabel
{

    private static final long serialVersionUID = 1L;

    @Override
    public final void setIcon( Icon icon )
    {
        Icon oldIcon = getIcon();
        super.setIcon( icon );
        PropertyChangeListener[] propertyChangeListeners = getPropertyChangeListeners();
        for ( PropertyChangeListener propertyChangeListener : propertyChangeListeners ) {
            propertyChangeListener.propertyChange( new PropertyChangeEvent( this, "icon", oldIcon, icon ) );
        }

    }

    public final Image getImage()
    {
        if ( this.getIcon() == null ) {
            return null;
        } else {
            return ( ( ImageIcon ) this.getIcon() ).getImage();
        }
    }

}
