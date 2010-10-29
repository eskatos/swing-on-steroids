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
package org.codeartisans.java.sos.threading;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Assert;

/**
 * @author Paul Merlin 
 */
@SuppressWarnings( "PublicInnerClass" )
public interface UseCase
{

    static class Util
    {

        static void testWorkQueue( WorkQueue workQueue )
                throws InterruptedException
        {

            DoneableRunnable work1 = new DoneableRunnable( 250 );
            DoneableRunnable work2 = new DoneableRunnable( 250 );

            workQueue.enqueue( work1 );
            Assert.assertFalse( work1.done );
            Thread.sleep( 300 );
            Assert.assertTrue( work1.done );
            work1.done = false;

            workQueue.enqueue( work1 );
            workQueue.enqueue( work2 );

            Assert.assertFalse( work1.done );
            Assert.assertFalse( work2.done );
            Thread.sleep( 300 );
            Assert.assertTrue( work1.done );
            Assert.assertTrue( work2.done );
            work1.done = false;
            work2.done = false;

        }

    }

    @SuppressWarnings( "PackageVisibleField" )
    static class DoneableRunnable
            implements Runnable
    {

        private final int delayMs;
        boolean done = false;

        public DoneableRunnable( int delayMs )
        {
            this.delayMs = delayMs;
        }

        @Override
        public void run()
        {
            try {
                Thread.sleep( delayMs );
                done = true;
            } catch ( InterruptedException ex ) {
                Logger.getLogger( GuiceWorkQueueTest.class.getName() ).log( Level.SEVERE, null, ex );
            }
        }

    }

}
