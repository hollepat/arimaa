package cz.cvut.fel.pjv.model;

import cz.cvut.fel.pjv.view.TimerPanel;
import cz.cvut.fel.pjv.pieces.ColorPiece;

public class Player {
    private final ColorPiece color;
    private TimerPanel timerPanel;
    private int timeLimit;
    private OwnTimer timer;
    private int turnCnt = 1;

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
        this.timer = new OwnTimer(timeLimit);
    }

    public char getNotation() {
        return switch (color) {
            case SILVER -> 's';
            case GOLD -> 'g';
        };
    }
    public ColorPiece getColor() {
        return color;
    }

    public void increaseTurn() {
        this.turnCnt++;
    }

    public void decreaseTurn() {
        this.turnCnt--;
    }

    public int getTurn() {
        return this.turnCnt;
    }


}
