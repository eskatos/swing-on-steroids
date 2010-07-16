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
