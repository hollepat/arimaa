package cz.cvut.fel.pjv.pieces;

import cz.cvut.fel.pjv.model.Player;

public class Elephant extends Piece{
    /**
     * Constructor for Piece
     *
     * @param positionX the x location of Piece
     * @param positionY the y location of Piece
     * @param alive     the state whether if alive or not
     * @param player    the Player associated with the Piece
     */
    public Elephant(char positionX, int positionY, boolean alive, Player player) {
        super(positionX, positionY, alive, player);
    }
}
