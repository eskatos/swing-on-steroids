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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

class ThreadedJButtonHasClickHandlers implements HasClickHandlers {

    private final JButton button;

    public ThreadedJButtonHasClickHandlers(JButton button) {
        this.button = button;
    }

    @Override
    public HandlerRegistration addClickHandler(final ClickHandler handler) {
        final ActionListener listener = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        handler.onClick(new ClickNotification());
                    }
                }).start();
            }
        };
        button.addActionListener(listener);
        return new HandlerRegistration() {

            @Override
            public void removeHandler() {
                button.removeActionListener(listener);
            }
        };

    }
}

