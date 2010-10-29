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
package org.codeartisans.java.sos.presenters;

import com.google.inject.Inject;

import org.codeartisans.java.sos.views.View;
import org.codeartisans.java.sos.views.notifications.MockHasClickHandler;
import org.codeartisans.java.sos.views.values.StringHasStringValue;
import org.codeartisans.java.sos.views.handlers.ClickHandler;
import org.codeartisans.java.sos.views.notifications.ClickNotification;
import org.codeartisans.java.sos.views.handlers.HasClickHandlers;
import org.codeartisans.java.sos.views.values.HasValue;

import org.junit.Assert;

import org.qi4j.api.injection.scope.Uses;

/**
 * @author Paul Merlin 
 */
@SuppressWarnings( "PublicInnerClass" )
public interface UseCase
{

    static class Util
    {

        static void testHelloPresenter( HelloPresenter presenter )
        {
            presenter.bind();
            presenter.view.input().setValue( "Sneak" );
            ( ( MockHasClickHandler<Void> ) presenter.view.sayHelloButton() ).click();
            Assert.assertEquals( "Hello Sneak", presenter.view.output().getValue() );

        }

    }

    static class HelloPresenter
            extends BasePresenter<HelloView>
            implements Presenter
    {

        @Inject
        public HelloPresenter( @Uses HelloView view )
        {
            super( view );
        }

        @Override
        public void onBind()
        {
            recordViewRegistration( view.sayHelloButton().addClickHandler( new ClickHandler<Void>()
            {

                @Override
                public void onClick( ClickNotification<Void> notification )
                {
                    view.output().setValue( "Hello " + view.input().getValue() );
                }

            } ) );
        }

        @Override
        public void onUnbind()
        {
        }

    }

    interface HelloView
            extends View
    {

        HasValue<String> input();

        HasClickHandlers<Void> sayHelloButton();

        HasValue<String> output();

    }

    static class HelloViewImpl
            implements HelloView
    {

        private final HasValue<String> input;
        private final HasValue<String> output;
        private final MockHasClickHandler<Void> sayHelloButton;

        public HelloViewImpl()
        {
            input = new StringHasStringValue();
            sayHelloButton = new MockHasClickHandler<Void>();
            output = new StringHasStringValue();
        }

        @Override
        public HasValue<String> input()
        {
            return input;
        }

        @Override
        public HasClickHandlers<Void> sayHelloButton()
        {
            return sayHelloButton;
        }

        @Override
        public HasValue<String> output()
        {
            return output;
        }

        @Override
        public void reveal()
        {
        }

        @Override
        public void hide()
        {
        }

        @Override
        public void busy()
        {
        }

        @Override
        public void done()
        {
        }

    }

}
