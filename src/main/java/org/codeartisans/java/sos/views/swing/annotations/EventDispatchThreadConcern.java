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
import java.lang.reflect.Method;
import javax.swing.SwingUtilities;

import org.codeartisans.java.sos.SOSFailure;

import org.qi4j.api.common.AppliesTo;
import org.qi4j.api.concern.GenericConcern;
import org.qi4j.api.injection.scope.Invocation;

/**
 * @author Paul Merlin
 */
@AppliesTo( EventDispatchThread.class )
public class EventDispatchThreadConcern
        extends GenericConcern
{

    @Invocation
    private EventDispatchThread annotation;

    @Override
    public Object invoke( final Object proxy, final Method method, final Object[] args )
            throws Throwable
    {
        if ( !Void.TYPE.equals( method.getReturnType() ) ) {
            throw new IllegalStateException( "EventDispatchThread allowed only on methods returning void" );
        }
        if ( SwingUtilities.isEventDispatchThread() ) {
            next.invoke( proxy, method, args );
        } else {
            Runnable runnable = new Runnable()
            {

                @Override
                public void run()
                {
                    try {
                        next.invoke( proxy, method, args );
                    } catch ( Throwable ex ) {
                        throw new SOSFailure( ex.getMessage(), ex );
                    }
                }

            };
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
