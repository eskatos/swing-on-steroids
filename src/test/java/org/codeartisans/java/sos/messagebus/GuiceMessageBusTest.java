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
package org.codeartisans.java.sos.messagebus;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Singleton;
import com.google.inject.name.Names;
import org.codeartisans.java.sos.threading.DefaultWorkQueue;
import org.codeartisans.java.sos.threading.WorkQueue;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Paul Merlin <paul@nosphere.org>
 */
public class GuiceMessageBusTest
{

    private class MessageBusTestModule extends AbstractModule
    {

        @Override
        protected void configure()
        {
            bind( String.class ).annotatedWith( Names.named( WorkQueue.NAME ) ).toInstance( "MessageBusTest" );
            bind( Integer.class ).annotatedWith( Names.named( WorkQueue.SIZE ) ).toInstance( 2 );
            bind( WorkQueue.class ).to( DefaultWorkQueue.class ).in( Singleton.class );
            bind( MessageBus.class ).to( SingleThreadDeliveryMessageBus.class ).in( Singleton.class );
        }

    }

    private MessageBus msgBus;

    @Before
    public void setUp()
    {
        msgBus = Guice.createInjector( new MessageBusTestModule() ).getInstance( MessageBus.class );

    }

    @After
    public void tearDown()
    {
        msgBus = null;
    }

    @Test
    public void test() throws InterruptedException
    {
        UseCase.Util.testMessageBus( msgBus );
    }

}
