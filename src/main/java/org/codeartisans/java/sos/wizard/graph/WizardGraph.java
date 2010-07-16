/*
 * Copyright (c) 2010 Paul Merlin <paul@nosphere.org>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.codeartisans.java.sos.wizard.graph;

import java.util.List;
import org.codeartisans.java.sos.wizard.model.WizardModel;
import org.codeartisans.java.sos.wizard.presenters.WizardPageID;

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
