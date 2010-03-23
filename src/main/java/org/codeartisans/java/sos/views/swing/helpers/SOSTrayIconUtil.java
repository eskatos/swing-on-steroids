/*
 * Copyright (c) 2009 Paul Merlin <paul@nosphere.org>
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
package org.codeartisans.java.sos.views.swing.helpers;

import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import javax.swing.JFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
        LOGGER.debug( "updateTrayIconLocation: {}, {}, {}", new Object[]{ trayX, trayY, trayIconLocation } );
    }

    public static void moveFrameNextToTrayIcon( JFrame frame )
    {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] gs = ge.getScreenDevices();
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
                frameX = 0;
                frameY = 0;
                break;
            case TOP_RIGHT:
                frameX = screenWidth - frameWidth;
                frameY = 0;
                break;
            case BOTTOM_LEFT:
                frameX = 0;
                frameX = screenHeight - frameHeight;
                break;
            case BOTTOM_RIGHT:
                frameX = screenWidth - frameWidth;
                frameY = screenHeight - frameHeight;
                break;
        }
        LOGGER.trace( "AFTER moveFrameNextToTrayIcon: " + frameX + "," + frameY + " in screen: " + screenWidth + "," + screenHeight );
        frame.setLocation( frameX, frameY );
    }

}
