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
package org.swing.on.steroids.presenters;

import org.swing.on.steroids.presenters.UseCase.HelloPresenter;
import org.swing.on.steroids.presenters.UseCase.HelloViewImpl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.qi4j.bootstrap.AssemblyException;
import org.qi4j.bootstrap.ModuleAssembly;
import org.qi4j.test.AbstractQi4jTest;

/**
 * @author Paul Merlin 
 */
public class Qi4jHelloPresenterTest
        extends AbstractQi4jTest
{

    @Override
    public void assemble( ModuleAssembly module )
            throws AssemblyException
    {
        module.addObjects( HelloViewImpl.class );
        module.addObjects( HelloPresenter.class );
    }

    private HelloPresenter presenter;

    @Before
    @Override
    public void setUp()
            throws Exception
    {
        super.setUp();
        presenter = objectBuilderFactory.newObject( HelloPresenter.class );
        presenter.bind();
    }

    @After
    @Override
    public void tearDown()
            throws Exception
    {
        presenter.unbind();
        presenter = null;
        super.tearDown();
    }

    @Test
    public void testHelloPresenter()
    {
        UseCase.Util.testHelloPresenter( presenter );
    }

}
