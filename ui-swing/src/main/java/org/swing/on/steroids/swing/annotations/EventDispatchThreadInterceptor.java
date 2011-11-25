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
package org.swing.on.steroids.swing.annotations;

import java.awt.EventQueue;
import javax.swing.SwingUtilities;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import org.swing.on.steroids.SOSFailure;

/**
 * Add this to your Guice module :
 * 
 *  bindInterceptor( Matchers.any(), Matchers.annotatedWith( EventDispatchTread.class ), new EventDispatchThreadInterceptor() );
 * 
 * @see http://code.google.com/p/google-guice/wiki/AOP
 */
public class EventDispatchThreadInterceptor
        implements MethodInterceptor
{

    @Override
    public Object invoke( final MethodInvocation invocation )
            throws Throwable
    {
        // Void method
        if ( Void.TYPE.equals( invocation.getMethod().getReturnType() ) ) {
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
                invokeEDT( annotation, runnable );
            }
            return null;

            // Method which return an object
        } else {
            if ( SwingUtilities.isEventDispatchThread() ) {
                return invocation.proceed();

            } else {
                final LovalVariable var = new LovalVariable();
                Runnable runnable = new Runnable()
                {

                    @Override
                    public void run()
                    {
                        try {
                            var.set( invocation.proceed() );
                        } catch ( Throwable ex ) {
                            throw new SOSFailure( ex.getMessage(), ex );
                        }
                    }

                };
                EventDispatchThread annotation = invocation.getMethod().getAnnotation( EventDispatchThread.class );
                if ( annotation.value() == EventDispatchThreadPolicy.invokeLater ) {
                    throw new IllegalArgumentException( "The methods which return an object must be called using the policy 'invokeAndWait'" );
                }
                invokeEDT( annotation, runnable );
                return var.get();
            }
        }
    }

    private void invokeEDT( EventDispatchThread annotation, Runnable runnable )
            throws Throwable
    {

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

    private static class LovalVariable
    {

        private Object obj;

        public void set( Object obj )
        {
            this.obj = obj;
        }

        public Object get()
        {
            return obj;
        }

    }

}
