package cz.cvut.fel.pjv.view;

import cz.cvut.fel.pjv.controller.Game;

import javax.swing.*;
import java.awt.*;
import java.sql.Time;
import java.util.logging.Level;

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
        Game.logger.log(Level.CONFIG, "Timer was initiated.");
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
