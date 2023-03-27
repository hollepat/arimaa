package cz.cvut.fel.pjv.model;

import cz.cvut.fel.pjv.pieces.Piece;

import javax.accessibility.Accessible;
import javax.swing.*;
import java.awt.*;
import java.awt.dnd.DropTargetListener;

/**
 *  one square of the game board --> abstract representation
 *
 */
public class Spot {


    private final TypeOfSpot typeOfSpot;
    private Piece currentPiece = null;
    private int x;
    private int y;

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

    public void setPiece(Piece piece) {
        this.currentPiece = piece;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }


}
