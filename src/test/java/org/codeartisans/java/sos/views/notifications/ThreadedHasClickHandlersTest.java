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

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.Test;
import org.junit.Assert;
import org.codeartisans.java.sos.sampleapp.domain.DefaultGreetService;
import org.codeartisans.java.sos.sampleapp.domain.GreetService;
import org.codeartisans.java.sos.sampleapp.presentation.views.GreetingsView;
import org.codeartisans.java.toolbox.guice.GuiceHelper;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;

public class ThreadedHasClickHandlersTest {

     private static class UnThreadedGreetingsPresenterTestGuiceModule
            extends AbstractModule
    {

        @Override
        protected void configure()
        {
            bind(GreetingsView.class).to(MockBlockintgJButtonedGreetingsView.class);
            bind(BlockingGreetingsPresenter.class);
            bind(GreetService.class).to(DefaultGreetService.class);
        }

    }

     private static class ThreadedGreetingsPresenterTestGuiceModule
            extends AbstractModule
    {

        @Override
        protected void configure()
        {
            bind(GreetingsView.class).to(MockUnBlockintgJButtonedGreetingsView.class);
            bind(BlockingGreetingsPresenter.class);
            bind(GreetService.class).to(DefaultGreetService.class);
        }

    }

    private static Injector injector;
    private BlockingGreetingsPresenter presenter;
    private MockBlockintgJButtonedGreetingsView blockingView;
    private MockUnBlockintgJButtonedGreetingsView unblockingView;

    @BeforeClass
    public static void beforeClass()
    {
        GuiceHelper.enableDebugOutput();
        injector = Guice.createInjector(new UnThreadedGreetingsPresenterTestGuiceModule());
    }

    @Before
    public void before()
    {
    }

    @Test
    public void testBlockingGreetingsPresenter()
    {

        injector = Guice.createInjector(new UnThreadedGreetingsPresenterTestGuiceModule());
        presenter = injector.getInstance(BlockingGreetingsPresenter.class);
        blockingView = (MockBlockintgJButtonedGreetingsView) presenter.view();

        presenter.bind();
        presenter.view().reveal();


        blockingView.name.setValue("Bob");
        blockingView.getButton().doClick();
        Assert.assertEquals("Hello Bob!", blockingView.message.getValue());

        blockingView.close.click();
        presenter.unbind();
    }

    @Test
    public void testNoBlockingGreetingsPresenter() throws InterruptedException
    {

        injector = Guice.createInjector(new ThreadedGreetingsPresenterTestGuiceModule());
        presenter = injector.getInstance(BlockingGreetingsPresenter.class);
        unblockingView = (MockUnBlockintgJButtonedGreetingsView) presenter.view();
        
        presenter.bind();
        presenter.view().reveal();


        unblockingView.name.setValue("Bob");
        unblockingView.getButton().doClick();
        Assert.assertNull(unblockingView.message.getValue());
        Thread.sleep(550);
        Assert.assertEquals("Hello Bob!", unblockingView.message.getValue());


        unblockingView.close.click();
        presenter.unbind();
    }

    @After
    public void after()
    {
    }

}
