package org.codeartisans.java.sos.sampleapp;

import org.junit.Test;

public class SampleGuiceAppTest
{

    @Test
    public void test()
            throws InterruptedException
    {
        SampleGuiceApp.main( new String[]{} );
        Thread.sleep( 100000 );
    }

}
