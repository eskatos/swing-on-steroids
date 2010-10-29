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
package org.codeartisans.java.sos.views.handlers;

/**
 * Indicates that the implementing type has button-like behavior.
 */
public interface HasButtonBehavior
        extends HasClickHandlers<Void>
{

    /**
     * Whether or not the button is enabled.
     * 
     * @return <code>true</code> if enabled, <code>false</code> otherwise
     */
    boolean isEnabled();

    /**
     * Set the enabled state of the button.
     *
     * @param enabled <code>true</code> to enable the button, <code>false</code> to disable it
     */
    void setEnabled( boolean enabled );

    /**
     * Whether or not the button is visible.
     *
     * @return <code>true</code> if visible, <code>false</code> otherwise
     */
    boolean isVisible();

    /**
     * Set the visibility of the button.
     *
     * @param visible <code>true</code> to show the button, <code>false</code> to hide it
     */
    void setVisible( boolean visible );

    /**
     * Simulate a click on the button.
     */
    void click();

}
