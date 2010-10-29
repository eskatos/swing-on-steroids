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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.codeartisans.java.sos.wizard.model.WizardPageID;
import org.codeartisans.java.sos.wizard.model.WizardModel;
import org.codeartisans.java.toolbox.CollectionUtils;
import org.codeartisans.java.toolbox.exceptions.NullArgumentException;

import org.jgrapht.alg.CycleDetector;
import org.jgrapht.alg.DirectedNeighborIndex;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.ClassBasedEdgeFactory;
import org.jgrapht.graph.ListenableDirectedGraph;

/**
 * Default WizardGraph implementation based on {@link DefaultDirectedGraph}.
 *
 * All modifications must be done through the {@link ListenableDirectedGraph} decorator in order to ensure that the
 * {@link DirectedNeighborIndex} is up to date.
 *
 * @author Paul Merlin
 */
@SuppressWarnings( "PackageVisibleField" )
public class DefaultWizardGraph<M extends WizardModel>
        extends DefaultDirectedGraph<PageVertex<M>, TransitionEdge>
        implements WizardGraph<M>
{

    private static final long serialVersionUID = 1L;
    private final ListenableDirectedGraph<PageVertex<M>, TransitionEdge> listenableDecorator;
    private final DirectedNeighborIndex<PageVertex<M>, TransitionEdge> neighborsIndex;
    /* package */ PageVertex<M> startPageVertex;
    /* package */ PageVertex<M> currentPageVertex;

    public DefaultWizardGraph()
    {
        super( new ClassBasedEdgeFactory<PageVertex<M>, TransitionEdge>( TransitionEdge.class ) );
        listenableDecorator = new ListenableDirectedGraph<PageVertex<M>, TransitionEdge>( this );
        neighborsIndex = new DirectedNeighborIndex<PageVertex<M>, TransitionEdge>( this );
        listenableDecorator.addGraphListener( neighborsIndex );
    }

    @Override
    public PageVertex<M> startPageVertex()
    {
        return startPageVertex;
    }

    @Override
    public PageVertex<M> previousPageVertex()
    {
        if ( currentPageVertex == null ) {
            return null;
        }
        Set<PageVertex<M>> predecessors = neighborsIndex.predecessorsOf( currentPageVertex );
        predecessors = filterEnabledPredecessors( currentPageVertex, predecessors, true );
        if ( predecessors.isEmpty() ) {
            return null;
        }
        return CollectionUtils.firstElementOrNull( predecessors );
    }

    @Override
    public PageVertex<M> currentPageVertex()
    {
        return currentPageVertex;
    }

    @Override
    public void setCurrentPageVertex( PageVertex<M> pageVertex )
    {
        if ( !containsVertex( pageVertex ) ) {
            throw new IllegalArgumentException( "WizardGraph does not contains this PageVertex: " + pageVertex );
        }
        if ( !currentWizardStepsPath().contains( pageVertex ) ) {
            throw new IllegalArgumentException( "PageVertex is not in the current WizardGraph steps path: " + pageVertex );
        }
        currentPageVertex = pageVertex;
    }

    @Override
    public PageVertex<M> nextPageVertex()
    {
        if ( currentPageVertex == null ) {
            return null;
        }
        Set<PageVertex<M>> successors = neighborsIndex.successorsOf( currentPageVertex );
        successors = filterEnabledSuccessors( currentPageVertex, successors, true );
        if ( successors.isEmpty() ) {
            return null;
        }
        return CollectionUtils.firstElementOrNull( successors );
    }

    @Override
    public PageVertex<M> getPageVertex( WizardPageID pageID )
    {
        NullArgumentException.ensureNotNull( "PageID", pageID );
        for ( PageVertex<M> eachPageVertex : vertexSet() ) {
            if ( eachPageVertex.wizardPageID().equals( pageID ) ) {
                return eachPageVertex;
            }
        }
        return null;
    }

    @Override
    public void addTransitionEdge( PageVertex<M> previous, PageVertex<M> next, Boolean enabled )
    {
        if ( !containsVertex( previous ) ) {
            listenableDecorator.addVertex( previous );
        }
        if ( !containsVertex( next ) ) {
            listenableDecorator.addVertex( next );
        }
        TransitionEdge transitionEdge = new TransitionEdge( previous, next, enabled );
        if ( containsEdge( transitionEdge ) ) {
            setTransitionEdgeEnabled( previous, next, enabled );
        } else {
            listenableDecorator.addEdge( previous, next, transitionEdge );
            updateStartPageVertex();
        }
    }

    @Override
    public void setTransitionEdgeEnabled( PageVertex<M> previous, PageVertex<M> next, Boolean enabled )
    {
        TransitionEdge transitionEdge = getEdge( previous, next );
        NullArgumentException.ensureNotNull( "Transition from " + previous + " to " + next, transitionEdge );
        transitionEdge.setEnabled( enabled );
        updateStartPageVertex();
    }

    @Override
    public void applyTransitionChanges( Iterable<TransitionChange> changes )
    {
        if ( changes != null ) {
            Map<TransitionEdge, Boolean> newStatuses = new HashMap<TransitionEdge, Boolean>();
            for ( TransitionChange eachChange : changes ) {
                PageVertex<M> previous = getPageVertex( eachChange.getPreviousID() );
                PageVertex<M> next = getPageVertex( eachChange.getNextID() );
                TransitionEdge eachEdge = getEdge( previous, next );
                NullArgumentException.ensureNotNull( "Transition from " + previous + " to " + next, eachEdge );
                newStatuses.put( eachEdge, eachChange.isEnabled() );
            }
            for ( Map.Entry<TransitionEdge, Boolean> eachEntry : newStatuses.entrySet() ) {
                eachEntry.getKey().setEnabled( eachEntry.getValue() );
            }
            updateStartPageVertex();
        }
    }

    private void updateStartPageVertex()
    {
        Set<PageVertex<M>> vertexSet = vertexSet();
        if ( !vertexSet.isEmpty() ) {
            if ( vertexSet.size() == 2 ) {
                startPageVertex = CollectionUtils.firstElementOrNull( vertexSet );
                currentPageVertex = startPageVertex;
            } else {
                List<PageVertex<M>> currentWizardStepsPath = currentWizardStepsPath();
                if ( !currentWizardStepsPath.isEmpty() ) {
                    startPageVertex = currentWizardStepsPath().get( 0 );
                }
            }
        }
    }

    @Override
    public List<PageVertex<M>> currentWizardStepsPath()
    {
        List<PageVertex<M>> stepsPath = new ArrayList<PageVertex<M>>();

        if ( currentPageVertex != null ) {

            List<PageVertex<M>> previouses = new ArrayList<PageVertex<M>>();
            PageVertex<M> eachPageVertex = assertOneEnabledPredecessorOrNullIfNone( currentPageVertex );
            while ( eachPageVertex != null ) {
                previouses.add( eachPageVertex );
                eachPageVertex = assertOneEnabledPredecessorOrNullIfNone( eachPageVertex );
            }

            List<PageVertex<M>> nexts = new ArrayList<PageVertex<M>>();
            eachPageVertex = assertOneEnabledSuccessorOrNullIfNone( currentPageVertex );
            while ( eachPageVertex != null ) {
                nexts.add( eachPageVertex );
                eachPageVertex = assertOneEnabledSuccessorOrNullIfNone( eachPageVertex );
            }

            Collections.reverse( previouses );
            stepsPath.addAll( previouses );
            stepsPath.add( currentPageVertex );
            stepsPath.addAll( nexts );
        }
        return stepsPath;
    }

    @Override
    public void assertStepsPathUnicity()
    {
        CycleDetector<PageVertex<M>, TransitionEdge> cd = new CycleDetector<PageVertex<M>, TransitionEdge>( this );
        if ( cd.detectCycles() ) {
            throw new WizardGraphHasCyclesException( cd.findCycles() );
        }
        // Prevent deactivated edges
        // StrongConnectivityInspector<PageVertex, TransitionEdge> sci = new StrongConnectivityInspector<PageVertex, TransitionEdge>( this );
        // if ( !sci.isStronglyConnected() ) {
        //     throw new WizardGraphIsNotStronglyConnected( sci.stronglyConnectedSets() );
        // }
    }

    private PageVertex<M> assertOneEnabledPredecessorOrNullIfNone( PageVertex<M> pageVertex )
    {
        Set<PageVertex<M>> predecessors = neighborsIndex.predecessorsOf( pageVertex );
        predecessors = filterEnabledPredecessors( pageVertex, predecessors, true );
        if ( !predecessors.isEmpty() && predecessors.size() > 1 ) {
            throw new RuntimeException();
        }
        return CollectionUtils.firstElementOrNull( predecessors );
    }

    private Set<PageVertex<M>> filterEnabledPredecessors( PageVertex<M> pivot, Set<PageVertex<M>> predecessors, boolean enabled )
    {
        Set<PageVertex<M>> filtered = new HashSet<PageVertex<M>>();
        for ( PageVertex<M> eachPredecessor : predecessors ) {
            TransitionEdge edge = this.getEdge( eachPredecessor, pivot );
            if ( edge.isEnabled() == enabled ) {
                filtered.add( eachPredecessor );
            }
        }
        return filtered;
    }

    private PageVertex<M> assertOneEnabledSuccessorOrNullIfNone( PageVertex<M> pageVertex )
    {
        Set<PageVertex<M>> successors = neighborsIndex.successorsOf( pageVertex );
        successors = filterEnabledSuccessors( pageVertex, successors, true );
        if ( !successors.isEmpty() && successors.size() > 1 ) {
            throw new RuntimeException();
        }
        return CollectionUtils.firstElementOrNull( successors );
    }

    private Set<PageVertex<M>> filterEnabledSuccessors( PageVertex<M> pivot, Set<PageVertex<M>> successors, boolean enabled )
    {
        Set<PageVertex<M>> filtered = new HashSet<PageVertex<M>>();
        for ( PageVertex<M> eachSuccessor : successors ) {
            TransitionEdge edge = this.getEdge( pivot, eachSuccessor );
            if ( edge.isEnabled() == enabled ) {
                filtered.add( eachSuccessor );
            }
        }
        return filtered;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder( "DefaultWizardGraph{\n" );
        sb.append( "\tVERTEXES\n" );
        for ( PageVertex<M> eachVertex : vertexSet() ) {
            sb.append( "\t\t" ).append( eachVertex ).append( "\n" );
        }
        sb.append( "\tEDGES\n" );
        for ( TransitionEdge eachEdge : edgeSet() ) {
            sb.append( "\t\t" ).append( eachEdge ).append( "\n" );
        }
        sb.append( "\tSTART   VERTEX " ).append( startPageVertex ).append( "\n" );
        sb.append( "\tCURRENT VERTEX " ).append( currentPageVertex ).append( "\n" );
        sb.append( "\tSTEPS PATHS    " ).append( Arrays.toString( currentWizardStepsPath().toArray() ) ).append( "\n" );
        return sb.append( "}" ).toString();
    }

}
