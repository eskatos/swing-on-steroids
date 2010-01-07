/*
 * Created on 7 janv. 2010
 *
 * Licenced under the Netheos Licence, Version 1.0 (the "Licence"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at :
 *
 * http://www.netheos.net/licences/LICENCE-1.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *
 * Copyright (c) Netheos
 */
package org.codeartisans.java.sos.views.notifications;

import javax.swing.JButton;
import org.codeartisans.java.sos.sampleapp.presentation.views.GreetingsView;
import org.codeartisans.java.sos.views.mock.notifications.MockHasClickHandler;
import org.codeartisans.java.sos.views.mock.notifications.StringHasStringValue;
import org.codeartisans.java.sos.views.swing.notifications.JButtonHasClickHandlers;
import org.codeartisans.java.sos.views.values.HasValue;

public class MockBlockintgJButtonedGreetingsView implements GreetingsView {

    final StringHasStringValue name = new StringHasStringValue();
    final MockHasClickHandler greet = new MockHasClickHandler();
    final StringHasStringValue message = new StringHasStringValue();
    final MockHasClickHandler close = new MockHasClickHandler();
    final JButton button = new JButton();

    @Override
    public HasValue<String> nameInput() {
        return name;
    }

    @Override
    public HasClickHandlers greetButton() {
        return new JButtonHasClickHandlers(button);
    }

    @Override
    public HasValue<String> messageDisplay() {
        return message;
    }

    @Override
    public HasClickHandlers closeButton() {
        return close;
    }

    @Override
    public void reveal() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void busy() {
    }

    @Override
    public void done() {
    }

    public JButton getButton() {
        return button;
    }
}
