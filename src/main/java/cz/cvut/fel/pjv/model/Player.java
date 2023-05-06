package cz.cvut.fel.pjv.model;

import cz.cvut.fel.pjv.view.TimerPanel;
import cz.cvut.fel.pjv.pieces.ColorPiece;

public class Player {
    private ColorPiece color;
    private String name;
    private TimerPanel timerPanel;
    private int timeLimit;
    private RealTimeClocks timer;
    private int turn = 0;
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

    public void increaseTurn() {
        this.turn++;
    }

    public void decreaseTurn() {
        this.turn--;
    }

    public int getTurn() {
        return this.turn;
    }

}
