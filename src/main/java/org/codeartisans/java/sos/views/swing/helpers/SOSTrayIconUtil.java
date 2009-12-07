package org.codeartisans.java.sos.views.swing.helpers;

import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import javax.swing.JFrame;

public class SOSTrayIconUtil
{

    //private static final Logger LOGGER = LoggerFactory.getLogger(SOSTrayIconUtil.class);
    public enum TrayIconLocation
    {

        TOP_LEFT, TOP_RIGHT,
        BOTTOM_LEFT, BOTTOM_RIGHT
    }

    private static int trayIconScreen = 0;
    private static TrayIconLocation trayIconLocation = TrayIconLocation.BOTTOM_RIGHT;

    public static void updateTrayIconLocation(int trayX, int trayY)
    {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] gs = ge.getScreenDevices();
        DisplayMode dm = gs[trayIconScreen].getDisplayMode();
        int screenWidth = dm.getWidth();
        int screenHeight = dm.getHeight();
        if (trayX < screenWidth / 2 && trayY < screenHeight / 2) {
            trayIconLocation = TrayIconLocation.TOP_LEFT;
        } else if (trayX >= screenWidth / 2 && trayY < screenHeight / 2) {
            trayIconLocation = TrayIconLocation.TOP_RIGHT;
        } else if (trayX < screenWidth / 2 && trayY >= screenHeight / 2) {
            trayIconLocation = TrayIconLocation.BOTTOM_LEFT;
        } else {
            trayIconLocation = TrayIconLocation.BOTTOM_RIGHT;
        }
        //LOGGER.debug("updateTrayIconLocation: " + trayX + "," + trayY + " " + trayIconLocation);
    }

    public static void moveFrameNextToTrayIcon(JFrame frame)
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
        //LOGGER.debug("BEFORE moveFrameNextToTrayIcon: " + frameWidth + "," + frameHeight + "  " + frameX + "," + frameY + " in screen: " + screenWidth + "," + screenHeight);
        switch (trayIconLocation) {
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
        //LOGGER.debug("AFTER moveFrameNextToTrayIcon: " + frameX + "," + frameY + " in screen: " + screenWidth + "," + screenHeight);
        frame.setLocation(frameX, frameY);
    }
}
