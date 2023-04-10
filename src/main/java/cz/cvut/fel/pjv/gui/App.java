package cz.cvut.fel.pjv.gui;

import javax.swing.*;
import java.awt.*;

public class App {

    public static void run() {
        LaunchScreen launchScreen = new LaunchScreen();
    }

    /**
     * Center JFrame to the middle of a screen when appears.
     * @param frame takes JFrame that is supposed to be put in center of screen
     */
    public static void setFrameCenter(JFrame frame) {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
    }
}
