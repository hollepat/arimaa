package cz.cvut.fel.pjv.model;

import cz.cvut.fel.pjv.controller.Game;
import cz.cvut.fel.pjv.pieces.Piece;

import java.util.logging.Level;


/**
 *  one square of the game board --> abstract representation
 *
 */
public class Spot {


    private final TypeOfSpot typeOfSpot;
    private Piece currentPiece = null;
    private final char x;
    private final int y;

    /**
     * Represents one spot on game board.
     *
     * @param x     {a,b,c,d,e,f,g,h}
     * @param y     {1,2,3,4,5,6,7,8}
     */
    public Spot(int y, int x, TypeOfSpot typeOfSpot) {
        char FIRST = 'a';
        this.x = (char)((int) FIRST + x);
        this.y = y + 1;
        Game.logger.log(Level.INFO, "Spot created: " + this.x + " " + this.y);
        this.typeOfSpot = typeOfSpot;
    }

    public Piece getPiece() {
        return this.currentPiece;
    }

    /**
     * Check whether Spot is free.
     * @return true if Piece is in Spot.
     */
    public boolean isOccupied() {
        return (this.currentPiece != null);
    }

    public void setPiece(Piece piece) {
        this.currentPiece = piece;
    }

    public char getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public TypeOfSpot getTypeOfSpot() {
        return typeOfSpot;
    }

    @Override
    public String toString() {
        return "Spot{" +
                ", currentPiece=" + currentPiece.toString() +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}
