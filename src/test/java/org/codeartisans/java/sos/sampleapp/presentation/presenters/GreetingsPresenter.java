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
package org.codeartisans.java.sos.sampleapp.presentation.presenters;

import com.google.inject.Inject;

import org.codeartisans.java.sos.presenters.BasePresenter;
import org.codeartisans.java.sos.presenters.Presenter;
import org.codeartisans.java.sos.sampleapp.domain.GreetService;
import org.codeartisans.java.sos.sampleapp.presentation.views.GreetingsView;
import org.codeartisans.java.sos.views.handlers.ClickHandler;
import org.codeartisans.java.sos.views.notifications.ClickNotification;
import org.codeartisans.java.toolbox.async.Callback;

/**
 * @author Paul Merlin 
 */
public class GreetingsPresenter
        extends BasePresenter<GreetingsView>
        implements Presenter
{

    private final GreetService greetService;

    @Inject
    public GreetingsPresenter( GreetingsView view, GreetService greetService )
    {
        super( view );
        this.greetService = greetService;
    }

    @Override
    public void onBind()
    {
        System.out.println( view );
        System.out.println( view.greetButton() );
        System.out.println( view.nameInput() );
        System.out.println( greetService );
        recordViewRegistration( view.greetButton().addClickHandler( new ClickHandler<Void>()
        {

            @Override
            public void onClick( ClickNotification<Void> notification )
            {
                view.busy();
                greetService.greet( view.nameInput().getValue(), new Callback<String>()
                {

                    @Override
                    public void onSuccess( String value )
                    {
                        view.doSomethingLongInEDT();
                        view.messageDisplay().setValue( value );
                        view.done();
                    }

                    @Override
                    @SuppressWarnings( "CallToThreadDumpStack" )
                    public void onError( String message, Throwable cause )
                    {
                        if ( cause != null ) {
                            cause.printStackTrace();
                        }
                        view.messageDisplay().setValue( "Unable to greet, see logs for details." );
                        view.done();
                    }

                } );
            }

        } ) );
        recordViewRegistration( view.closeButton().addClickHandler( new ClickHandler<Void>()
        {

            @Override
            public void onClick( ClickNotification<Void> notification )
            {
                view.hide();
            }

        } ) );
    }

    @Override
    public void onUnbind()
    {
    }

}
