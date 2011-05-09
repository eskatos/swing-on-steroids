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
package org.codeartisans.java.sos.sampleapp.presentation.views;

import org.swing.on.steroids.views.View;
import org.swing.on.steroids.views.handlers.HasClickHandlers;
import org.swing.on.steroids.views.values.HasValue;

public interface GreetingsView
        extends View
{

    HasValue<String> nameInput();

    HasClickHandlers<Void> greetButton();

    HasValue<String> messageDisplay();

    HasClickHandlers<Void> closeButton();

    void doSomethingLongInEDT();

}