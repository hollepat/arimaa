package cz.cvut.fel.pjv.model;

import cz.cvut.fel.pjv.pieces.Piece;

import javax.accessibility.Accessible;
import javax.swing.*;
import java.awt.*;
import java.awt.dnd.DropTargetListener;

public class Spot extends JPanel {     // one square of the game board --> abstract representation of some kind might not use, since I encounterd some problems with extending JPanel and it's behaviour in GridLayout


    private final TypeOfSpot typeOfSpot;
    private Piece piece = null;
    private int x;
    private int y;

    /**
     * Represents one spot on game board.
     *
     * @param x     {1,2,3,4,5,6,7,8}
     * @param y     {1,2,3,4,5,6,7,8} that respond to letters {a,b,c,d,e,f,g,h}
     */
    public Spot(int x, int y, TypeOfSpot typeOfSpot) {
        super(true);
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

    public int getY() {
        return y;
    }


    public void drawSpot() {

    }
}
