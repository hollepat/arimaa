package cz.cvut.fel.pjv.model;

import cz.cvut.fel.pjv.pieces.ColorPiece;

public class Player {
    private final ColorPiece color;
    private int turnCnt = 1;

    /**
     * Constructor for Player.
     * @param color of player (GOLD, SILVER)
     */
    public Player(ColorPiece color) {
        this.color = color;

    }

    /**
     * Get char for color of Player following rules of arimaa notation.
     * @return color in arimaa notation
     */
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
