package cz.cvut.fel.pjv.gui;

import cz.cvut.fel.pjv.model.Game;

import javax.swing.*;
import java.awt.*;
import java.sql.Time;

public class TimerPanel extends JPanel {

    private Game game;
    private Time goldTime;
    private Time silverTime;


    public TimerPanel(Game game) {
        super(new BorderLayout());
        this.game = game;
        goldTime = Time.valueOf("00:00:00");
        silverTime = Time.valueOf("00:00:00");
        init();
    }

    private void init() {
        // TODO create components
    }

    /**
     * Update time on hours by one second.
     */
    public void tik() {

    }
}
