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
package org.swing.on.steroids.wizard.swing;

import com.google.inject.Inject;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Frame;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.swing.on.steroids.views.handlers.HasButtonBehavior;
import org.swing.on.steroids.swing.BaseSwingFrameView;
import org.swing.on.steroids.swing.SwingWrappersFactory;
import org.swing.on.steroids.swing.helpers.SwingHelper;
import org.swing.on.steroids.swing.notifications.FrameTitleHasStringValue;
import org.swing.on.steroids.views.values.HasValue;
import org.swing.on.steroids.wizard.model.WizardModel;
import org.swing.on.steroids.wizard.model.WizardPageID;
import org.swing.on.steroids.wizard.views.WizardBlockingView;
import org.swing.on.steroids.wizard.views.WizardPageView;
import org.swing.on.steroids.wizard.views.WizardView;
import org.swing.on.steroids.wizard.swing.components.SwingWizardBlockingGlassPane;
import org.swing.on.steroids.wizard.swing.components.SwingWizardButtonBarPanel;
import org.swing.on.steroids.wizard.swing.components.SwingWizardFrame;
import org.swing.on.steroids.wizard.swing.components.SwingWizardPagesStackPanel;
import org.swing.on.steroids.wizard.swing.components.SwingWizardStepsPanel;

/**
 * @author Paul Merlin
 */
public class SwingWizardView<M extends WizardModel>
        extends BaseSwingFrameView
        implements WizardView<M>
{

    private SwingWizardFrame delegate;
    private SwingWizardBlockingGlassPane blockingGlassPane;
    private HasValue<String> wizardTitle;
    private SwingWizardStepsPanel stepsPanel;
    private final List<String> steps = new ArrayList<String>();
    private String currentStep;
    private SwingWizardPagesStackPanel pagesStackPanel;
    private final List<WizardPageView> pageViewsArray = new ArrayList<WizardPageView>();
    private final Map<WizardPageID, WizardPageView> pageViews = new HashMap<WizardPageID, WizardPageView>();
    private WizardPageView currentPageView;
    private SwingWizardButtonBarPanel buttonBarPanel;
    private HasButtonBehavior cancel;
    private HasButtonBehavior previous;
    private HasButtonBehavior next;
    private HasButtonBehavior finish;

    @Inject
    public SwingWizardView( final SwingWrappersFactory swingWrappersFactory )
    {
        SwingHelper.invokeAndWait( new Runnable()
        {

            @Override
            public void run()
            {
                delegate = new SwingWizardFrame();
                blockingGlassPane = new SwingWizardBlockingGlassPane();
                wizardTitle = new FrameTitleHasStringValue( delegate );
                stepsPanel = new SwingWizardStepsPanel();
                pagesStackPanel = new SwingWizardPagesStackPanel();
                buttonBarPanel = new SwingWizardButtonBarPanel();
                cancel = swingWrappersFactory.createJButtonHasButtonBehavior( buttonBarPanel.cancel() );
                previous = swingWrappersFactory.createJButtonHasButtonBehavior( buttonBarPanel.previous() );
                next = swingWrappersFactory.createJButtonHasButtonBehavior( buttonBarPanel.next() );
                finish = swingWrappersFactory.createJButtonHasButtonBehavior( buttonBarPanel.finish() );
                delegate.setGlassPane( blockingGlassPane );
                delegate.setButtonBarComponent( buttonBarPanel );
                delegate.setStepsComponent( stepsPanel );
                delegate.setPagesStackComponent( pagesStackPanel );
            }

        } );
    }

    @Override
    public HasValue<String> title()
    {
        return wizardTitle;
    }

    @Override
    public void setSteps( final Iterable<String> titles )
    {
        SwingHelper.invokeAndWait( new Runnable()
        {

            @Override
            public void run()
            {
                steps.clear();
                stepsPanel.clearSteps();
                for ( String eachTitle : titles ) {
                    steps.add( eachTitle );
                    stepsPanel.addStep( eachTitle );
                }
            }

        } );
    }

    @Override
    public void addStep( final String title )
    {
        SwingHelper.invokeAndWait( new Runnable()
        {

            @Override
            public void run()
            {
                if ( steps.isEmpty() || !steps.get( steps.size() - 1 ).equals( title ) ) {
                    steps.add( title );
                    stepsPanel.addStep( title );
                }
            }

        } );
    }

    @Override
    public void setCurrentStep( final String title )
    {
        SwingHelper.invokeAndWait( new Runnable()
        {

            @Override
            public void run()
            {
                if ( !steps.contains( title ) ) {
                    throw new IllegalArgumentException( "Unknown step: " + title );
                }
                currentStep = title;
                stepsPanel.setCurrent( steps.indexOf( currentStep ) );
            }

        } );
    }

    @Override
    public void addPageView( final WizardPageID wizardPageID, final WizardPageView pageView )
    {
        SwingHelper.invokeAndWait( new Runnable()
        {

            @Override
            public void run()
            {
                if ( pageViewsArray.contains( pageView ) ) {
                    throw new IllegalStateException( "Page view already added" );
                }
                pageViewsArray.add( pageView );
                pageViews.put( wizardPageID, pageView );
            }

        } );
    }

    @Override
    public void showPage( final WizardPageID wizardPageID )
    {
        SwingHelper.invokeAndWait( new Runnable()
        {

            @Override
            public void run()
            {
                currentPageView = pageViews.get( wizardPageID );
                pagesStackPanel.setPageComponent( ( Component ) currentPageView.uiComponent() );
            }

        } );
    }

    @Override
    public void showBlockingView( final WizardBlockingView blockingView )
    {
        SwingHelper.invokeAndWait( new Runnable()
        {

            @Override
            public void run()
            {
                blockingGlassPane.setBlockingComponent( ( Component ) blockingView.uiComponent() );
                blockingGlassPane.setVisible( true );
            }

        } );
    }

    @Override
    public void removeBlockingView()
    {
        SwingHelper.invokeAndWait( new Runnable()
        {

            @Override
            public void run()
            {
                blockingGlassPane.setVisible( false );
            }

        } );
    }

    @Override
    public HasButtonBehavior cancelButton()
    {
        return cancel;
    }

    @Override
    public HasButtonBehavior previousButton()
    {
        return previous;
    }

    @Override
    public HasButtonBehavior nextButton()
    {
        return next;
    }

    @Override
    public HasButtonBehavior finishButton()
    {
        return finish;
    }

    @Override
    protected Frame delegate()
    {
        return delegate;
    }

}
