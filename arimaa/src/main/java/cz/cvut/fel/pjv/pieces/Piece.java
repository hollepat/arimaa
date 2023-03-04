package cz.cvut.fel.pjv.pieces;

abstract public class Piece {

    private char positionX;     // a, b, c, d, e, f, g, h
    private int positionY;      // 1, 2, 3, 4, 5, 6, 7, 8
    private boolean live;       // live = true, killed = false

    public boolean move(char x, int y) {
        // TODO implement
        return true;
    }



}
