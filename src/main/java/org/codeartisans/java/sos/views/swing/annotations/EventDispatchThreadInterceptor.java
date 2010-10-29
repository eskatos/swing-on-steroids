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
package org.codeartisans.java.sos.views.swing.annotations;

import java.awt.EventQueue;
import javax.swing.SwingUtilities;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import org.codeartisans.java.sos.SOSFailure;

/**
 * @author Paul Merlin
 */
public class EventDispatchThreadInterceptor
        implements MethodInterceptor
{

    public EventDispatchThreadInterceptor()
    {
    }

    @Override
    public Object invoke( final MethodInvocation invocation )
            throws Throwable
    {
        if ( !Void.TYPE.equals( invocation.getMethod().getReturnType() ) ) {
            throw new IllegalStateException( "EventDispatchThread allowed only on methods returning void" );
        }
        if ( SwingUtilities.isEventDispatchThread() ) {
            invocation.proceed();
        } else {
            Runnable runnable = new Runnable()
            {

                @Override
                public void run()
                {
                    try {
                        invocation.proceed();
                    } catch ( Throwable ex ) {
                        throw new SOSFailure( ex.getMessage(), ex );
                    }
                }

            };
            EventDispatchThread annotation = invocation.getMethod().getAnnotation( EventDispatchThread.class );
            switch ( annotation.value() ) {
                case invokeLater:
                    EventQueue.invokeLater( runnable );
                    break;
                case invokeAndWait:
                default:
                    EventQueue.invokeAndWait( runnable );
                    break;
            }
        }
        return null;
    }

}
