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
package org.swing.on.steroids.forking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * StreamGobbler is used by ForkService implementations to consume the forked processes standard/error output.
 */
public final class StreamGobbler
        extends Thread
{

    private static final Logger LOGGER = LoggerFactory.getLogger( StreamGobbler.class );
    private InputStream is;
    private OutputStream redirect;

    public StreamGobbler( InputStream is, ThreadGroup threadGroup, String name )
    {
        super( threadGroup, name );
        this.is = is;
    }

    public StreamGobbler( InputStream is, ThreadGroup threadGroup, String name, OutputStream redirect )
    {
        this( is, threadGroup, name );
        this.redirect = redirect;
    }

    @Override
    public void run()
    {
        try {
            PrintWriter pw = null;
            if ( redirect != null ) {
                pw = new PrintWriter( redirect );
            }
            final BufferedReader br = new BufferedReader( new InputStreamReader( is ) );
            String line = null;
            while ( ( line = br.readLine() ) != null ) {
                if ( pw != null ) {
                    pw.println( line );
                }
            }
            if ( pw != null ) {
                pw.flush();
            }
            br.close();
        } catch ( IOException ex ) {
            LOGGER.warn( "IOE during StreamGobbler run", ex );
        }
    }

}
