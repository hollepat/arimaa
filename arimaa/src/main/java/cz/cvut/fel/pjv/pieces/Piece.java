package cz.cvut.fel.pjv.pieces;

import cz.cvut.fel.pjv.model.Player;

// images in svg https://commons.wikimedia.org/wiki/Category:Arimaa_pieces_(SVG_set_by_Risteall)
abstract public class Piece {

    private char positionX;     // a, b, c, d, e, f, g, h
    private int positionY;      // 1, 2, 3, 4, 5, 6, 7, 8
    private boolean alive;       // live = true, killed = false
    private Player player;

    /**
     * Constructor for Piece
     * @param positionX     the x location of Piece
     * @param positionY     the y location of Piece
     * @param alive         the state whether if alive or not
     * @param player        the Player associated with the Piece
     */
    public Piece(char positionX, int positionY, boolean alive, Player player) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.alive = alive;
        this.player = player;
    }

    public boolean move(char x, int y) {
        // TODO implement
        return true;
    }



}
