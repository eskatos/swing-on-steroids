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
 * @author Paul Merlin 
 */
public class GuiceHelloPresenterTest
{

    private class HelloPresenterTestModule
            extends AbstractModule
    {

        @Override
        protected void configure()
        {
            bind( HelloView.class ).to( HelloViewImpl.class );
            bind( HelloPresenter.class );
        }

    }

    private HelloPresenter presenter;

    @Before
    public void setUp()
    {
        presenter = Guice.createInjector( new HelloPresenterTestModule() ).getInstance( HelloPresenter.class );
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
        presenter.view.input().setValue( "Sneak" );
        ( ( MockHasClickHandler ) presenter.view.sayHelloButton() ).click();
        Assert.assertEquals( "Hello Sneak", presenter.view.output().getValue() );
    }

}
