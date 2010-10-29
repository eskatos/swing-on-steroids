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
package org.codeartisans.java.sos.views.notifications;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.google.inject.name.Names;

import org.codeartisans.java.sos.sampleapp.domain.DefaultGreetService;
import org.codeartisans.java.sos.sampleapp.domain.GreetService;
import org.codeartisans.java.sos.sampleapp.presentation.views.GreetingsView;
import org.codeartisans.java.sos.threading.DefaultWorkQueue;
import org.codeartisans.java.sos.threading.WorkQueue;
import org.codeartisans.java.sos.views.swing.SwingWrappersFactory;

import org.junit.Test;
import org.junit.Assert;
import org.junit.After;

public class ThreadedHasClickHandlersTest
{

    private static class ThreadedGreetingsPresenterTestGuiceModule
            extends AbstractModule
    {

        @Override
        protected void configure()
        {
            bind( GreetingsView.class ).to( MockUnBlockingJButtonedGreetingsView.class );
            bind( BlockingGreetingsPresenter.class );
            bind( GreetService.class ).to( DefaultGreetService.class );
            bind( SwingWrappersFactory.class );
            bind( String.class ).annotatedWith( Names.named( WorkQueue.NAME ) ).toInstance( "ThreadedJButtonTest" );
            bind( Integer.class ).annotatedWith( Names.named( WorkQueue.SIZE ) ).toInstance( 2 );
            bind( WorkQueue.class ).to( DefaultWorkQueue.class ).in( Singleton.class );
        }

    }

    private static Injector injector;
    private BlockingGreetingsPresenter presenter;
    private MockUnBlockingJButtonedGreetingsView unblockingView;

    @Test
    public void testNoBlockingGreetingsPresenter()
            throws InterruptedException
    {

        injector = Guice.createInjector( new ThreadedGreetingsPresenterTestGuiceModule() );
        presenter = injector.getInstance( BlockingGreetingsPresenter.class );
        unblockingView = ( MockUnBlockingJButtonedGreetingsView ) presenter.view();

        presenter.bind();
        presenter.view().reveal();


        unblockingView.name.setValue( "Bob" );
        unblockingView.getButton().doClick();
        Assert.assertNull( unblockingView.message.getValue() );
        Thread.sleep( 550 );
        Assert.assertEquals( "Hello Bob!", unblockingView.message.getValue() );


        unblockingView.close.click();
        presenter.unbind();
    }

    @After
    public void after()
    {
    }

}
