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
