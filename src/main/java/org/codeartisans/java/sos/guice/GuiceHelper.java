/*
 * Copyright (c) 2010, Paul Merlin. All Rights Reserved.
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
package org.codeartisans.java.sos.guice;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public final class GuiceHelper
{

    private GuiceHelper()
    {
    }

    public static void enableDebugOutput()
    {
        Logger guiceLogger = Logger.getLogger( "com.google.inject" );
        guiceLogger.addHandler( new ConsoleHandler()
        {

            {
                setLevel( Level.ALL );
                setFormatter( new java.util.logging.Formatter()
                {

                    @Override
                    public String format( LogRecord record )
                    {
                        return String.format( "[Guice] %s%n", record.getMessage() );
                    }

                } );
            }

        } );
        guiceLogger.setLevel( Level.ALL );
    }

}
