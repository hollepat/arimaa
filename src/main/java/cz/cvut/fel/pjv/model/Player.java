package cz.cvut.fel.pjv.model;

import cz.cvut.fel.pjv.view.TimerPanel;
import cz.cvut.fel.pjv.pieces.ColorPiece;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Player {
    private final ColorPiece color;
    private int turnCnt = 1;

    public Player(ColorPiece color) {
        this.color = color;

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
