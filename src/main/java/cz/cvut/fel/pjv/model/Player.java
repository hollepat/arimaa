package cz.cvut.fel.pjv.model;

import cz.cvut.fel.pjv.gui.TimerPanel;
import cz.cvut.fel.pjv.pieces.ColorPiece;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;

public class Player {
    private ColorPiece color;
    private String name;
    private TimerPanel timerPanel;
    private int timeLimit;
    private RealTimeClocks timer;

    public Player(ColorPiece color) {
        this.color = color;
    }

    public Player(ColorPiece color, TimerPanel timerPanel, int timeLimit) {
        this.color = color;
        this.timerPanel = timerPanel;
        this.timeLimit = timeLimit;
        initTimer();
    }

    private void initTimer() {
        this.timer = new RealTimeClocks(timerPanel, timeLimit);
    }


    public ColorPiece getColor() {
        return color;
    }
}
