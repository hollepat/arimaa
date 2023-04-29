package cz.cvut.fel.pjv.model;


import cz.cvut.fel.pjv.pieces.*;


public class Move {

    private final Piece piece;
    private final char sx;
    private final int sy;
    private final char dx;
    private final int dy;
    private final Player player;
    private int moveNumInTurn;  // indicates in which of 1...4 moves was this move played (one turn)
    public boolean pushPromise = false;
    private String arimaaAnotation;

    public Move(Piece piece, char sx, int sy, char dx, int dy, Player player, int moveNumInTurn) {
        this.piece = piece;
        this.sx = sx;
        this.sy = sy;
        this.dx = dx;
        this.dy = dy;
        this.player = player;
        this.moveNumInTurn = moveNumInTurn;
    }

    public Move(Piece piece, char sx, int sy, char dx, int dy, Player player) {
        this.piece = piece;
        this.sx = sx;
        this.sy = sy;
        this.dx = dx;
        this.dy = dy;
        this.player = player;
    }

    public char getSx() {
        return sx;
    }

    public int getSy() {
        return sy;
    }

    public char getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }

    public Piece getPiece() {
        return piece;
    }

    public int getMoveNumInTurn() {
        return moveNumInTurn;
    }

    public Player getPlayer() {
        return player;
    }
}
