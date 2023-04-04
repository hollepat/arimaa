package cz.cvut.fel.pjv.model;

import cz.cvut.fel.pjv.pieces.Piece;


/**
 *  one square of the game board --> abstract representation
 *
 */
public class Spot {


    private final TypeOfSpot typeOfSpot;
    private Piece currentPiece = null;
    private final int x;
    private final int y;

    /**
     * Represents one spot on game board.
     *
     * @param x     {1,2,3,4,5,6,7,8}
     * @param y     {1,2,3,4,5,6,7,8} that respond to letters {a,b,c,d,e,f,g,h}
     */
    public Spot(int x, int y, TypeOfSpot typeOfSpot) {
        this.x = x;
        this.y = y;
        this.typeOfSpot = typeOfSpot;
    }

    public Piece getPiece() {
        return this.currentPiece;
    }

    public boolean isOccupied() {
        return this.currentPiece != null;
    }

    public void setPiece(Piece piece) {
        this.currentPiece = piece;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public TypeOfSpot getTypeOfSpot() {
        return typeOfSpot;
    }
}
