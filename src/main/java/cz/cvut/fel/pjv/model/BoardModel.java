package cz.cvut.fel.pjv.model;


import cz.cvut.fel.pjv.pieces.Piece;

import javax.swing.*;

/**
 * Abstract representation of board. It handel, where each Piece is located on board
 */
public class BoardModel {

    private int BOARD_DIMENSION = 8;

    private Piece[][] arimaaBoardArray = new Piece[8][8];


    public BoardModel() {
        initSquares();
        initPieces();
    }

    /**
     * Get Piece on coordination in Board
     * @param x [a, b, c, d, e, f, g, h]
     * @param y [1, 2, 3, 4, 5, 6, 7, 8]
     * @return  Piece
     */
    public Piece getPiece(char x, int y) {
        if (x < 'a' || x > 'h' || y < 1 || y > 8) {
            return null;
        } else {
            return arimaaBoardArray[x - 'a'][y - 1];
        }
    }

    /**
     * Do move in BoardModel. Change position of Piece/Pieces.
     */
    public void doMove() {

    }

    /**
     * Undo move in BoardModel. Change position of last moved Piece/Pieces.
     */
    public void undoMove() {

    }

    private void initPieces() {
        // TODO set Pieces to according Square
    }

    private void initSquares() {
        // TODO init each Square
    }
}
