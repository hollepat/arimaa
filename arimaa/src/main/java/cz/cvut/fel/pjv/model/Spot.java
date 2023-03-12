package cz.cvut.fel.pjv.model;

import cz.cvut.fel.pjv.pieces.Piece;

import javax.swing.*;

public class Spot extends JPanel {     // one square of the game board


    private final TypeOfSpot typeOfSpot;
    private Piece piece;
    private int x;
    private char y;

    /**
     * Represents one spot on game board.
     *
     * @param piece reference to piece on these coordination's, if no piece on spot --> null
     * @param x     {1,2,3,4,5,6,7,8}
     * @param y     {1,2,3,4,5,6,7,8} that respond to letters {a,b,c,d,e,f,g,h}
     */
    public Spot(Piece piece, int x, char y, TypeOfSpot typeOfSpot) {
        this.piece = piece;
        this.x = x;
        this.y = y;
        this.typeOfSpot = typeOfSpot;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(char y) {
        this.y = y;
    }
}
