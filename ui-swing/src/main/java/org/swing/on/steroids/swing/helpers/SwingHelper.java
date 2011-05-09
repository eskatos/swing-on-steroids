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
package org.swing.on.steroids.swing.helpers;

import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.lang.reflect.InvocationTargetException;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.LookAndFeel;
import javax.swing.RepaintManager;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.jdesktop.swinghelper.debug.CheckThreadViolationRepaintManager;
import org.jdesktop.swinghelper.debug.EventDispatchThreadHangMonitor;

/**
 * Collection of static methods dealing with Swing application init.
 */
public final class SwingHelper
{

    private SwingHelper()
    {
    }

    /**
     * Register enforcers to ensure the EDT stays clean.
     */
    public static void initSafeSwing()
    {
        RepaintManager.setCurrentManager( new CheckThreadViolationRepaintManager() );
        EventDispatchThreadHangMonitor.initMonitoring();
    }

    /**
     * Register SwingHelper UncaughtExceptionHandler and an AwtHandler for modal dialogs.
     */
    public static void initExceptionHandling()
    {
        Thread.setDefaultUncaughtExceptionHandler( new SteroidUncaughtExceptionHandler() );
        System.setProperty( "sun.awt.exception.handler", SteroidAwtHandler.class.getName() );
    }

    /**
     * Init given Look And Feel.
     * @param className LAF class name
     */
    public static void initLookAndFeel( final String className )
    {
        try {
            SwingUtilities.invokeAndWait( new Runnable()
            {

                @Override
                public void run()
                {
                    try {
                        JFrame.setDefaultLookAndFeelDecorated( true );
                        JDialog.setDefaultLookAndFeelDecorated( true );
                        UIManager.setLookAndFeel( className );
                    } catch ( Exception ex ) {
                        throw new SwingFault( ex.getMessage(), ex );
                    }
                }

            } );
        } catch ( InterruptedException ex ) {
            throw new SwingFault( ex.getMessage(), ex );
        } catch ( InvocationTargetException ex ) {
            throw new SwingFault( ex.getMessage(), ex );
        }
    }

    public static void initLookAndFeel( final LookAndFeel lookAndFeel )
    {
        try {
            SwingUtilities.invokeAndWait( new Runnable()
            {

                @Override
                public void run()
                {
                    try {
                        JFrame.setDefaultLookAndFeelDecorated( true );
                        JDialog.setDefaultLookAndFeelDecorated( true );
                        UIManager.setLookAndFeel( lookAndFeel );
                    } catch ( Exception ex ) {
                        throw new SwingFault( ex.getMessage(), ex );
                    }
                }

            } );
        } catch ( InterruptedException ex ) {
            throw new SwingFault( ex.getMessage(), ex );
        } catch ( InvocationTargetException ex ) {
            throw new SwingFault( ex.getMessage(), ex );
        }
    }

    public static void invokeAndWait( Runnable runnable )
    {
        try {
            EventQueue.invokeAndWait( runnable );
        } catch ( InterruptedException ex ) {
            throw new SwingFault( "Unable to invoke and wait: " + ex.getMessage(), ex );
        } catch ( InvocationTargetException ex ) {
            throw new SwingFault( "Unable to invoke and wait: " + ex.getMessage(), ex );
        }
    }

    public static Graphics2D addAntiAliasing( Graphics g )
    {
        Graphics2D g2d = ( Graphics2D ) g;
        g2d.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );
        return g2d;
    }

}
