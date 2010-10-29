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

import com.google.inject.Inject;

import java.util.ArrayList;
import java.util.List;

import org.codeartisans.java.sos.wizard.events.NavigationEvent;
import org.codeartisans.java.sos.wizard.model.WizardModel;
import org.codeartisans.java.sos.wizard.model.WizardPageID;
import org.codeartisans.java.sos.wizard.views.WizardPageView;
import org.codeartisans.java.sos.wizard.views.WizardView;
import org.codeartisans.java.toolbox.StringUtils;
import org.codeartisans.java.toolbox.exceptions.NullArgumentException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * FIXME Merge {@link BaseWizardPresenter} and {@link DefaultWizardPresenter} and provide hooks to Wizards implementors.
 * FIXME This will need to refactor theses two classes so that the resulting one is not a giant monster !
 * 
 * @author Paul Merlin
 */
public class DefaultWizardPresenter<M extends WizardModel>
        extends BaseWizardPresenter<M>
        implements WizardPresenter<M>
{

    private static final Logger LOGGER = LoggerFactory.getLogger( DefaultWizardPresenter.class );
    private WizardGraph<M> graph = new DefaultWizardGraph<M>();
    private final List<WizardPagePresenter<M, ? extends WizardPageView>> pagePresenters = new ArrayList<WizardPagePresenter<M, ? extends WizardPageView>>();

    @Inject
    public DefaultWizardPresenter( String wizardTitle, M wizardModel, WizardView<M> wizardView )
    {
        super( wizardTitle, wizardModel, wizardView );
    }

    @Override
    protected final void beforeBind()
    {
        LOGGER.trace( "beforeBind" );
        graph.assertStepsPathUnicity();
        for ( WizardPagePresenter<?, ?> eachPagePresenter : pagePresenters ) {
            eachPagePresenter.bind();
        }
    }

    @Override
    protected void afterBind()
    {
        LOGGER.trace( "afterBind" );
        LOGGER.trace( "Graph {}", graph.toString() );
        moveToWizardPage( graph.startPageVertex().wizardPageID(), false, false, false );
    }

    @Override
    protected final void beforeUnbind()
    {
        LOGGER.trace( "beforeUnbind" );
        for ( WizardPagePresenter<?, ?> eachPagePresenter : pagePresenters ) {
            eachPagePresenter.unbind();
        }
    }

    @Override
    public final void addTransition( WizardPagePresenter<M, ? extends WizardPageView> previous, WizardPagePresenter<M, ? extends WizardPageView> next, Boolean enabled )
    {
        NullArgumentException.ensureNotNull( "Previous WizardPagePresenter", previous );
        NullArgumentException.ensureNotNull( "Next WizardPagePresenter", next );
        if ( LOGGER.isTraceEnabled() ) {
            LOGGER.trace( "addTransistion({}, {}, {})", new Object[]{ previous.wizardPageID(), next.wizardPageID(), enabled } );
        }

        // Add reference to {@link WizardPageView}s in {@link WizardView} if needed.
        // This call {@link WizardPagePresenter#onPageAdded(WizardPageHelper)}.
        if ( !pagePresenters.contains( previous ) ) {
            view.addPageView( previous.wizardPageID(), previous.view() );
            pagePresenters.add( previous );
            previous.onPageAdded( wizardHelper );
        }
        if ( !pagePresenters.contains( next ) ) {
            view.addPageView( next.wizardPageID(), next.view() );
            pagePresenters.add( next );
            next.onPageAdded( wizardHelper );
        }

        // Update graph
        PageVertex<M> previousVertex = new PageVertex<M>( previous );
        PageVertex<M> nextVertex = new PageVertex<M>( next );
        graph.addTransitionEdge( previousVertex, nextVertex, enabled );

        // Update stepsView
        updateStepsView();
    }

    @Override
    public final void applyTransitionChanges( Iterable<TransitionChange> changes )
    {
        if ( LOGGER.isTraceEnabled() ) {
            LOGGER.trace( "Graph state BEFORE applyTransitionChanges()\n{}", graph.toString() );
        }

        // Apply changes
        graph.applyTransitionChanges( changes );

        if ( LOGGER.isTraceEnabled() ) {
            LOGGER.trace( "Graph state AFTER applyTransitionChanges()\n{}", graph.toString() );
        }

        // Update stepsView
        updateStepsView();
    }

    /**
     * Update stepsView according to {@link WizardGraph#currentWizardStepsPath()} and {@link WizardGraph#currentPageVertex()}.
     * Neighboor {@link WizardPagePresenter}s having the same title are shown as the same step.
     * {@link WizardPagePresenter}s with a null or empty title are hidden.
     */
    private void updateStepsView()
    {
        List<PageVertex<M>> currentWizardStepsPath = graph.currentWizardStepsPath();
        List<String> steps = new ArrayList<String>( currentWizardStepsPath.size() );
        String previousTitle = null;
        for ( PageVertex<M> eachStep : currentWizardStepsPath ) {
            String eachTitle = eachStep.presenter().title();
            if ( !StringUtils.isEmpty( eachTitle ) && ( previousTitle == null || !previousTitle.equals( eachTitle ) ) ) {
                steps.add( eachStep.presenter().title() );
            }
        }
        view.setSteps( steps );
        view.setCurrentStep( graph.currentPageVertex().presenter().title() );
    }

    @Override
    protected final WizardPageID previousPageID()
    {
        PageVertex<M> previous = graph.previousPageVertex();
        if ( previous == null ) {
            return null;
        }
        return previous.wizardPageID();
    }

    @Override
    protected final WizardPageID nextPageID()
    {
        PageVertex<M> next = graph.nextPageVertex();
        if ( next == null ) {
            return null;
        }
        return next.wizardPageID();
    }

    @Override
    protected final void moveToWizardPage( WizardPageID pageID, boolean fireBeforeNext, boolean fireAfterNext, boolean fireBeforeShow )
    {
        if ( LOGGER.isTraceEnabled() ) {
            LOGGER.trace( "moveToWizardPage({}, {}, {}, {})", new Object[]{ pageID, fireBeforeNext, fireAfterNext, fireBeforeShow } );
        }
        NullArgumentException.ensureNotNull( "WizardPageID", pageID );

        PageVertex<M> currentVertex = graph.currentPageVertex(); // Is null when showing the very first wizard page
        PageVertex<M> previousVertex = graph.previousPageVertex(); // Is null when the wizard starts
        PageVertex<M> nextVertex = graph.nextPageVertex(); // Is null when the wizard ends
        PageVertex<M> targetVertex = graph.getPageVertex( pageID );

        if ( previousVertex == null ) {
            setButtonEnabled( WizardButton.previous, false );
        }

        if ( ( previousVertex != null && !pageID.equals( previousVertex.wizardPageID() ) )
                && ( nextVertex != null && !pageID.equals( nextVertex.wizardPageID() ) ) ) {
            throw new IllegalArgumentException( "Given page is not previous nor next is the wizard graph" );
        }
        if ( previousVertex != null ) {
            if ( pageID.equals( previousVertex.wizardPageID() ) ) {
                targetVertex = previousVertex;
            } else {
                targetVertex = nextVertex;
            }
        }

        final NavigationEvent event = new NavigationEvent( currentVertex == null ? null : currentVertex.wizardPageID(), targetVertex.wizardPageID() );
        event.setFireBeforeNext( fireBeforeNext );
        event.setFireAfterNext( fireAfterNext );
        event.setFireBeforeShow( fireBeforeShow );

        // Before we can leave the current page, fire beforeNext. If it cancels the event, we return.
        if ( currentVertex != null && event.getFireBeforeNext() ) {
            currentVertex.presenter().beforeNext( event );
            if ( !event.isAlive() ) {
                return;
            }
        }

        // beforeNext() is the only method that can cancel the event it is also the only method that can redirect it
        WizardPageID newDestinationID = event.getDestinationPageID();
        if ( newDestinationID != targetVertex.presenter().wizardPageID() ) {
            // beforeNext() changed the destination page.
            moveToWizardPage( event.getDestinationPageID(), event.getFireBeforeNext(), event.getFireAfterNext(), event.getFireBeforeShow() );
            return;
        }

        // fire beforeFirstShow if needed
        fireBeforeFirstShowIfNeeded( targetVertex.presenter() );

        // fire beforeShow
        if ( event.getFireBeforeShow() ) {
            targetVertex.presenter().beforeShow();
        }

        // Show the page
        view.showPage( targetVertex.wizardPageID() );
        view.setCurrentStep( targetVertex.presenter().title() );

        // fire afterNext
        if ( currentVertex != null && event.getFireAfterNext() ) {
            currentVertex.presenter().afterNext();
        }
        graph.setCurrentPageVertex( targetVertex );

        // Set the values of our buttons based on
        // the current page position
        autoAdjustPreviousNextButtonStates();
    }

}
