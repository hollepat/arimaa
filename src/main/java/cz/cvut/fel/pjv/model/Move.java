package cz.cvut.fel.pjv.model;


import cz.cvut.fel.pjv.pieces.*;

import java.util.HashMap;
import java.util.Map;


public class Move {

    private final Piece piece;
    private final char sx;
    private final int sy;
    private final char dx;
    private final int dy;
    private final Player player;
    private int moveNumInTurn;  // indicates in which of 1...4 moves was this move played (one turn)
    private Map<String, Piece> killedPieces;
    public boolean pushPromise = false;

    public Move(Piece piece, char sx, int sy, char dx, int dy, Player player, int moveNumInTurn) {
        this.piece = piece;
        this.sx = sx;
        this.sy = sy;
        this.dx = dx;
        this.dy = dy;
        this.player = player;
        this.moveNumInTurn = moveNumInTurn;
        killedPieces = new HashMap<>();
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

    public Map<String, Piece> getKilledPieces() {
        return killedPieces;
    }

    public void addKilledPiece(String string, Piece piece) {
        this.killedPieces.put(string, piece);
    }

    @Override
    public String toString() {
        return "Moved " + piece.toString() +
                " from= " + sx + " " + sy +
                " to= " + dx + " " + dy +
                " pushPromise= " + pushPromise;
    }
}
