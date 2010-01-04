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
package org.codeartisans.java.sos.presenters;

import com.google.inject.Inject;
import org.codeartisans.java.sos.views.View;
import org.codeartisans.java.sos.views.mock.notifications.MockHasClickHandler;
import org.codeartisans.java.sos.views.mock.notifications.StringHasStringValue;
import org.codeartisans.java.sos.views.notifications.ClickHandler;
import org.codeartisans.java.sos.views.notifications.ClickNotification;
import org.codeartisans.java.sos.views.notifications.HasClickHandlers;
import org.codeartisans.java.sos.views.values.HasValue;
import org.junit.Assert;
import org.qi4j.api.injection.scope.Uses;

/**
 * @author Paul Merlin <paul@nosphere.org>
 */
public interface UseCase
{

    static class Util
    {

        static void testHelloPresenter(HelloPresenter presenter)
        {
            presenter.bind();
            presenter.view.input().setValue("Sneak");
            ((MockHasClickHandler) presenter.view.sayHelloButton()).click();
            Assert.assertEquals("Hello Sneak", presenter.view.output().getValue());

        }

    }

    static class HelloPresenter
            extends BasePresenter<HelloView>
            implements Presenter
    {

        @Inject
        public HelloPresenter(@Uses HelloView view)
        {
            super(view);
        }

        @Override
        public View view()
        {
            return view;
        }

        @Override
        public void onBind()
        {
            recordViewRegistration(view.sayHelloButton().addClickHandler(new ClickHandler()
            {

                @Override
                public void onClick(ClickNotification notification)
                {
                    view.output().setValue("Hello " + view.input().getValue());
                }

            }));
        }

        @Override
        public void onUnbind()
        {
        }

    }

    interface HelloView extends View
    {

        HasValue<String> input();

        HasClickHandlers sayHelloButton();

        HasValue<String> output();

    }

    static class HelloViewImpl implements HelloView
    {

        private final HasValue<String> input;
        private final HasValue<String> output;
        private final MockHasClickHandler sayHelloButton;

        public HelloViewImpl()
        {
            input = new StringHasStringValue();
            sayHelloButton = new MockHasClickHandler();
            output = new StringHasStringValue();
        }

        @Override
        public HasValue<String> input()
        {
            return input;
        }

        @Override
        public HasClickHandlers sayHelloButton()
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
