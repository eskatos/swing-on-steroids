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

import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.Window;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings( "PublicInnerClass" )
public final class SOSTrayIconUtil
{

    private static final Logger LOGGER = LoggerFactory.getLogger( SOSTrayIconUtil.class );

    public enum TrayIconLocation
    {

        TOP_LEFT, TOP_RIGHT,
        BOTTOM_LEFT, BOTTOM_RIGHT
    }

    private static int trayIconScreen = 0;
    private static TrayIconLocation trayIconLocation = TrayIconLocation.BOTTOM_RIGHT;

    private SOSTrayIconUtil()
    {
    }

    public static void updateTrayIconLocation( int trayX, int trayY )
    {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] gs = ge.getScreenDevices();
        DisplayMode dm = gs[trayIconScreen].getDisplayMode();
        int screenWidth = dm.getWidth();
        int screenHeight = dm.getHeight();
        if ( trayX < screenWidth / 2 && trayY < screenHeight / 2 ) {
            trayIconLocation = TrayIconLocation.TOP_LEFT;
        } else if ( trayX >= screenWidth / 2 && trayY < screenHeight / 2 ) {
            trayIconLocation = TrayIconLocation.TOP_RIGHT;
        } else if ( trayX < screenWidth / 2 && trayY >= screenHeight / 2 ) {
            trayIconLocation = TrayIconLocation.BOTTOM_LEFT;
        } else {
            trayIconLocation = TrayIconLocation.BOTTOM_RIGHT;
        }
        LOGGER.trace( "updateTrayIconLocation: {}, {}, {}", new Object[]{ trayX, trayY, trayIconLocation } );
    }

    public static void moveFrameNextToTrayIcon( Window frame )
    {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] gs = ge.getScreenDevices();
        Insets insets = Toolkit.getDefaultToolkit().getScreenInsets( ge.getDefaultScreenDevice().getDefaultConfiguration() );
        DisplayMode dm = gs[trayIconScreen].getDisplayMode();
        int screenWidth = dm.getWidth();
        int screenHeight = dm.getHeight();
        int frameWidth = frame.getWidth();
        int frameHeight = frame.getHeight();
        int frameX = frame.getX();
        int frameY = frame.getY();
        LOGGER.trace( "BEFORE moveFrameNextToTrayIcon: " + frameWidth + "," + frameHeight + "  " + frameX + "," + frameY + " in screen: " + screenWidth + "," + screenHeight );
        switch ( trayIconLocation ) {
            case TOP_LEFT:
                frameX = insets.left;
                frameY = insets.top;
                break;
            case TOP_RIGHT:
                frameX = screenWidth - frameWidth - insets.right;
                frameY = insets.top;
                break;
            case BOTTOM_LEFT:
                frameX = insets.left;
                frameX = screenHeight - frameHeight - insets.bottom;
                break;
            case BOTTOM_RIGHT:
                frameX = screenWidth - frameWidth - insets.right;
                frameY = screenHeight - frameHeight - insets.bottom;
                break;
        }
        LOGGER.trace( "AFTER moveFrameNextToTrayIcon: " + frameX + "," + frameY + " in screen: " + screenWidth + "," + screenHeight );
        frame.setLocation( frameX, frameY );
    }

}
