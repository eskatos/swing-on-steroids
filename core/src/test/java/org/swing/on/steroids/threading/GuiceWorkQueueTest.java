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
package org.swing.on.steroids.threading;

import org.swing.on.steroids.threading.WorkQueue;
import org.swing.on.steroids.threading.DefaultWorkQueue;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Singleton;
import com.google.inject.name.Names;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Paul Merlin 
 */
public class GuiceWorkQueueTest
{

    private class WorkQueueTestModule
            extends AbstractModule
    {

        @Override
        protected void configure()
        {
            bind( String.class ).annotatedWith( Names.named( WorkQueue.NAME ) ).toInstance( "WorkQueueTest" );
            bind( Integer.class ).annotatedWith( Names.named( WorkQueue.SIZE ) ).toInstance( 2 );
            bind( WorkQueue.class ).to( DefaultWorkQueue.class ).in( Singleton.class );
        }

    }

    private WorkQueue workQueue;

    @Before
    public void setUp()
    {
        workQueue = Guice.createInjector( new WorkQueueTestModule() ).getInstance( WorkQueue.class );

    }

    @After
    public void tearDown()
    {
        workQueue = null;
    }

    @Test
    public void test()
            throws InterruptedException
    {
        UseCase.Util.testWorkQueue( workQueue );
    }

}
