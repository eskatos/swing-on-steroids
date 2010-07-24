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
package org.codeartisans.java.sos.wizard;

import org.codeartisans.java.sos.wizard.presenters.WizardGraph;
import org.codeartisans.java.sos.wizard.presenters.DefaultWizardGraph;
import org.codeartisans.java.sos.wizard.presenters.PageVertex;
import java.util.Arrays;

import org.codeartisans.java.sos.threading.DefaultWorkQueue;
import org.codeartisans.java.sos.threading.WorkQueue;
import org.codeartisans.java.sos.views.swing.SwingWrappersFactory;
import org.codeartisans.java.sos.wizard.model.SignupWizardModel;
import org.codeartisans.java.sos.wizard.presenters.AccountPagePresenter;
import org.codeartisans.java.sos.wizard.presenters.CongratsPagePresenter;
import org.codeartisans.java.sos.wizard.presenters.CreditCardPagePresenter;
import org.codeartisans.java.sos.wizard.presenters.WelcomePagePresenter;
import org.codeartisans.java.sos.wizard.views.AccountPageView;
import org.codeartisans.java.sos.wizard.views.CongratsPageView;
import org.codeartisans.java.sos.wizard.views.CreditCardPageView;
import org.codeartisans.java.sos.wizard.views.PlanTypeConfirmBlockingView;
import org.codeartisans.java.sos.wizard.views.WelcomePageView;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * @author Paul Merlin
 */
public class WizardGraphTest
{

    @Test
    public void testWizardGraph()
    {
        WorkQueue workQueue = new DefaultWorkQueue( "WorkQueue", 5 );
        SwingWrappersFactory swf = new SwingWrappersFactory( workQueue );

        PageVertex<SignupWizardModel> welcome = new PageVertex<SignupWizardModel>( new WelcomePagePresenter( new WelcomePageView() ) );
        PageVertex<SignupWizardModel> account = new PageVertex<SignupWizardModel>( new AccountPagePresenter( new AccountPageView( swf ), new PlanTypeConfirmBlockingView( swf ) ) );
        PageVertex<SignupWizardModel> card = new PageVertex<SignupWizardModel>( new CreditCardPagePresenter( new CreditCardPageView( swf ) ) );
        PageVertex<SignupWizardModel> congrats = new PageVertex<SignupWizardModel>( new CongratsPagePresenter( new CongratsPageView() ) );

        WizardGraph<SignupWizardModel> graph = new DefaultWizardGraph<SignupWizardModel>();

        graph.addTransitionEdge( welcome, account, Boolean.TRUE );
        graph.addTransitionEdge( account, congrats, Boolean.TRUE );
        graph.addTransitionEdge( account, card, Boolean.FALSE );
        graph.addTransitionEdge( card, congrats, Boolean.FALSE );

        graph.assertStepsPathUnicity();

        soutGraphData( graph );

        assertTrue( graph.currentWizardStepsPath().containsAll( Arrays.asList( new PageVertex[]{ welcome, account, congrats } ) ) );

        try {
            graph.setCurrentPageVertex( card );
            fail( "Expected an exception" );
        } catch ( IllegalArgumentException ignored ) {
        }

        graph.setCurrentPageVertex( account );

        soutGraphData( graph );

        graph.setTransitionEdgeEnabled( account, congrats, Boolean.FALSE );
        graph.setTransitionEdgeEnabled( account, card, Boolean.TRUE );
        graph.setTransitionEdgeEnabled( card, congrats, Boolean.TRUE );

        graph.assertStepsPathUnicity();

        soutGraphData( graph );

        assertTrue( graph.currentWizardStepsPath().containsAll( Arrays.asList( new PageVertex[]{ welcome, account, card, congrats } ) ) );

        graph.setCurrentPageVertex( card );

        soutGraphData( graph );
    }

    private void soutGraphData( WizardGraph<SignupWizardModel> graph )
    {
        System.out.println( "==================================================================================================" );
        System.out.println( "StartPageVertex: " + graph.startPageVertex() );
        System.out.println( "CurrentPageVertex: " + graph.currentPageVertex() );
        System.out.println( "CurrentWizardStepsPath: " + Arrays.toString( graph.currentWizardStepsPath().toArray() ) );
        System.out.println( "WizardGraph.toString():\n" + graph.toString() );
        System.out.println();
        System.out.println( "==================================================================================================" );
        System.out.println();
    }

}
