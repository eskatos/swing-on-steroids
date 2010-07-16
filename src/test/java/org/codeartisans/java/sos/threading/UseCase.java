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
