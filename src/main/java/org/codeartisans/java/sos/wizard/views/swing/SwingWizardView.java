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
package org.codeartisans.java.sos.wizard.views.swing;

import com.google.inject.Inject;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Frame;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codeartisans.java.sos.views.handlers.HasButtonBehavior;
import org.codeartisans.java.sos.views.swing.BaseSwingFrameView;
import org.codeartisans.java.sos.views.swing.SwingWrappersFactory;
import org.codeartisans.java.sos.views.swing.helpers.SwingHelper;
import org.codeartisans.java.sos.views.swing.notifications.FrameTitleHasStringValue;
import org.codeartisans.java.sos.views.values.HasValue;
import org.codeartisans.java.sos.wizard.model.WizardModel;
import org.codeartisans.java.sos.wizard.model.WizardPageID;
import org.codeartisans.java.sos.wizard.views.WizardBlockingView;
import org.codeartisans.java.sos.wizard.views.WizardPageView;
import org.codeartisans.java.sos.wizard.views.WizardView;
import org.codeartisans.java.sos.wizard.views.swing.components.SwingWizardBlockingGlassPane;
import org.codeartisans.java.sos.wizard.views.swing.components.SwingWizardButtonBarPanel;
import org.codeartisans.java.sos.wizard.views.swing.components.SwingWizardFrame;
import org.codeartisans.java.sos.wizard.views.swing.components.SwingWizardPagesStackPanel;
import org.codeartisans.java.sos.wizard.views.swing.components.SwingWizardStepsPanel;

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
