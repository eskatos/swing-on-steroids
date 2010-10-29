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
package org.codeartisans.java.sos.wizard.model;

/**
 * WizardPageID is a class used to identify a page.
 *
 * Pages with the same WizardPageID should refer to the same WizardPage instance.
 * 
 * @author Paul Merlin
 */
public class WizardPageID
{

    private static int nextHashCode;
    private final int index;

    @SuppressWarnings( value = "ValueOfIncrementOrDecrementUsed" )
    public WizardPageID()
    {
        index = ++nextHashCode;
    }

    @Override
    public final int hashCode()
    {
        return index;
    }

    @Override
    public String toString()
    {
        return new StringBuilder( "PageID@" ).append( index ).toString();
    }

}
