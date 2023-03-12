package cz.cvut.fel.pjv.gui;

import javax.swing.*;
import java.awt.*;

public class SetUpScreen {
    // TODO screen that will take all the parameters to set up game and create Game !!!

    public void start() {
        JFrame frame = new JFrame("Arimaa");
        frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(320, 240));       // set size of frame
        Container container = frame.getContentPane();
    }
}
