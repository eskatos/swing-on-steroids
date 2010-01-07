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

import com.google.inject.Inject;
import org.codeartisans.java.sos.presenters.BasePresenter;
import org.codeartisans.java.sos.presenters.Presenter;
import org.codeartisans.java.sos.sampleapp.domain.GreetService;
import org.codeartisans.java.sos.sampleapp.presentation.views.GreetingsView;
import org.codeartisans.java.toolbox.async.Callback;

/**
 *
 * @author jmt
 */
public class BlockingGreetingsPresenter
         extends BasePresenter<GreetingsView>
        implements Presenter
{

    private final GreetService greetService;

    /**
     *
     * @param view
     * @param greetService
     */
    @Inject
    public BlockingGreetingsPresenter(GreetingsView view, GreetService greetService)
    {
        super(view);
        this.greetService = greetService;
    }

    @Override
    public void onBind()
    {
        recordViewRegistration(view.greetButton().addClickHandler(new ClickHandler()
        {

            @Override
            public void onClick(ClickNotification notification)
            {
                view.busy();

                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                }
                
                greetService.greet(view.nameInput().getValue(), new Callback<String>()
                {

                    @Override
                    public void onSuccess(String value)
                    {
                        view.messageDisplay().setValue(value);
                        view.done();
                    }

                    @Override
                    public void onError(String message, Throwable cause)
                    {
                        cause.printStackTrace();
                        view.messageDisplay().setValue("Unable to greet, see logs for details.");
                        view.done();
                    }

                });
            }

        }));
        recordViewRegistration(view.closeButton().addClickHandler(new ClickHandler()
        {

            @Override
            public void onClick(ClickNotification notification)
            {
                view.hide();
            }

        }));
    }

    @Override
    public void onUnbind()
    {
    }
}
