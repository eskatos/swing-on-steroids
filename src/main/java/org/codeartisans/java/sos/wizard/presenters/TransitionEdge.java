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
package org.codeartisans.java.sos.wizard.presenters;

import org.codeartisans.java.sos.wizard.model.WizardModel;

import org.jgrapht.graph.DefaultEdge;

/**
 * @author Paul Merlin
 */
class TransitionEdge
        extends DefaultEdge
{

    private static final long serialVersionUID = 1L;
    private PageVertex<? extends WizardModel> previous;
    private PageVertex<? extends WizardModel> next;
    private Boolean enabled;

    public TransitionEdge()
    {
        enabled = false;
    }

    public TransitionEdge( PageVertex<? extends WizardModel> previous, PageVertex<? extends WizardModel> next, Boolean enabled )
    {
        this.previous = previous;
        this.next = next;
        this.enabled = enabled;
    }

    public PageVertex<? extends WizardModel> previous()
    {
        return previous;
    }

    public PageVertex<? extends WizardModel> next()
    {
        return next;
    }

    public Boolean isEnabled()
    {
        return enabled;
    }

    public void setEnabled( Boolean enabled )
    {
        this.enabled = enabled;
    }

    @Override
    public String toString()
    {
        return new StringBuilder( "( " ).append( enabled ? "ENABLED " : "DISABLED " ).
                append( previous ).append( " : " ).append( next ).append( " )" ).toString();
    }

}
