package cz.cvut.fel.pjv.model;


import cz.cvut.fel.pjv.pieces.Piece;


public class Move {

    private Piece piece;
    private char sx;
    private int sy;
    private char dx;
    private int dy;
    private boolean valid;

    public Move(Piece piece, char sx, int sy, char dx, int dy) {
        this.piece = piece;
        this.sx = sx;
        this.sy = sy;
        this.dx = dx;
        this.dy = dy;
    }
}
