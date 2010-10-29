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
package org.codeartisans.java.sos.wizard.presenters;

import java.util.List;

import org.codeartisans.java.sos.wizard.model.WizardPageID;
import org.codeartisans.java.sos.wizard.model.WizardModel;

/**
 * A graph representing the wizard flow.
 *
 * FIXME Support only wizard graphs with one and only one enabled path at a time.
 *
 * @author Paul Merlin
 */
public interface WizardGraph<M extends WizardModel>
{

    PageVertex<M> startPageVertex();

    void addTransitionEdge( PageVertex<M> previous, PageVertex<M> next, Boolean enabled );

    void setTransitionEdgeEnabled( PageVertex<M> previous, PageVertex<M> next, Boolean enabled );

    void applyTransitionChanges( Iterable<TransitionChange> changes );

    PageVertex<M> previousPageVertex();

    PageVertex<M> currentPageVertex();

    void setCurrentPageVertex( PageVertex<M> pageVertex );

    PageVertex<M> nextPageVertex();

    PageVertex<M> getPageVertex( WizardPageID pageID );

    List<PageVertex<M>> currentWizardStepsPath();

    void assertStepsPathUnicity();

}
