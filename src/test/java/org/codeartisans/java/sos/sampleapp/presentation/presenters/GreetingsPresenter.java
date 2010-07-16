/*
 * Copyright (c) 2009 Paul Merlin <paul@nosphere.org>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
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
        recordViewRegistration( view.greetButton().addClickHandler( new ClickHandler()
        {

            @Override
            public void onClick( ClickNotification notification )
            {
                view.busy();
                greetService.greet( view.nameInput().getValue(), new Callback<String>()
                {

                    @Override
                    public void onSuccess( String value )
                    {
                        view.messageDisplay().setValue( value );
                        view.done();
                    }

                    @Override
                    public void onError( String message, Throwable cause )
                    {
                        cause.printStackTrace();
                        view.messageDisplay().setValue( "Unable to greet, see logs for details." );
                        view.done();
                    }

                } );
            }

        } ) );
        recordViewRegistration( view.closeButton().addClickHandler( new ClickHandler()
        {

            @Override
            public void onClick( ClickNotification notification )
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
