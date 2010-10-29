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
package org.codeartisans.java.sos.messagebus;

import org.codeartisans.java.sos.threading.WorkQueueComposite;
import org.codeartisans.java.sos.threading.WorkQueueConfiguration;

import org.junit.Test;

import org.qi4j.api.service.ServiceReference;
import org.qi4j.bootstrap.AssemblyException;
import org.qi4j.bootstrap.ModuleAssembly;
import org.qi4j.entitystore.memory.MemoryEntityStoreService;
import org.qi4j.spi.uuid.UuidIdentityGeneratorService;
import org.qi4j.test.AbstractQi4jTest;

/**
 * @author Paul Merlin 
 */
public class Qi4jMessageBusTest
        extends AbstractQi4jTest
{

    @Override
    @SuppressWarnings( "unchecked" )
    public void assemble( ModuleAssembly module )
            throws AssemblyException
    {
        module.addServices( MemoryEntityStoreService.class, UuidIdentityGeneratorService.class );
        module.addEntities( WorkQueueConfiguration.class );
        module.addServices( WorkQueueComposite.class );
        module.addServices( MessageBusComposite.class ).withMixins( MultiThreadDeliveryMixin.class );
    }

    @Test
    public void testMessageBus()
            throws InterruptedException
    {
        ServiceReference<MessageBusComposite> ref = serviceLocator.findService( MessageBusComposite.class );
        MessageBus messageBus = ref.get();

        UseCase.Util.testMessageBus( messageBus );
    }

}
