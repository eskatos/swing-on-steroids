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

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import org.codeartisans.java.sos.presenters.UseCase.HelloPresenter;
import org.codeartisans.java.sos.presenters.UseCase.HelloView;
import org.codeartisans.java.sos.presenters.UseCase.HelloViewImpl;
import org.codeartisans.java.sos.views.notifications.MockHasClickHandler;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Paul Merlin <paul@nosphere.org>
 */
public class GuiceHelloPresenterTest
{

    private class HelloPresenterTestModule extends AbstractModule
    {

        @Override
        protected void configure()
        {
            bind(HelloView.class).to(HelloViewImpl.class);
            bind(HelloPresenter.class);
        }

    }

    private HelloPresenter presenter;

    @Before
    public void setUp()
    {
        presenter = Guice.createInjector(new HelloPresenterTestModule()).getInstance(HelloPresenter.class);
        presenter.bind();
    }

    @After
    public void tearDown()
    {
        presenter.unbind();
        presenter = null;
    }

    @Test
    public void testHelloPresenter()
    {
        presenter.bind();
        presenter.view.input().setValue("Sneak");
        ((MockHasClickHandler) presenter.view.sayHelloButton()).click();
        Assert.assertEquals("Hello Sneak", presenter.view.output().getValue());
    }

}
