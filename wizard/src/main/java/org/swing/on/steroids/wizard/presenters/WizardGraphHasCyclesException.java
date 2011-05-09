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
package org.swing.on.steroids.wizard.presenters;

import java.util.Arrays;
import java.util.Set;

/**
 * @author Paul Merlin
 */
class WizardGraphHasCyclesException
        extends RuntimeException
{

    private static final long serialVersionUID = 1L;
    private final Set<?> cycles;

    WizardGraphHasCyclesException( Set<?> cycles )
    {
        this.cycles = cycles;
    }

    @Override
    public String getMessage()
    {
        return Arrays.toString( cycles.toArray() );
    }

}
