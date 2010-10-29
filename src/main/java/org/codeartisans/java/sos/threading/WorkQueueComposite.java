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

import org.codeartisans.java.toolbox.async.ErrorCallbackAdapter;

import org.qi4j.api.configuration.Configuration;
import org.qi4j.api.injection.scope.This;
import org.qi4j.api.mixin.Mixins;
import org.qi4j.api.service.ServiceComposite;

@Mixins( WorkQueueComposite.Mixin.class )
@SuppressWarnings( "PublicInnerClass" )
public interface WorkQueueComposite
        extends WorkQueue, ServiceComposite
{

    abstract class Mixin
            implements WorkQueueComposite
    {

        @This
        private Configuration<WorkQueueConfiguration> config;
        private DefaultWorkQueue delegate;

        @Override
        public void enqueue( Runnable runnable )
        {
            ensureDelegate().enqueue( runnable );
        }

        @Override
        public void enqueue( Runnable runnable, ErrorCallbackAdapter<RuntimeException> errorCallback )
        {
            ensureDelegate().enqueue( runnable, errorCallback );
        }

        private WorkQueue ensureDelegate()
        {
            if ( delegate == null ) {
                WorkQueueConfiguration cfg = config.configuration();
                delegate = new DefaultWorkQueue( cfg.name().get(), cfg.size().get() );
            }
            return delegate;
        }

    }

}
