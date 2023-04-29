package cz.cvut.fel.pjv.model;


import cz.cvut.fel.pjv.pieces.ColorPiece;
import cz.cvut.fel.pjv.pieces.Piece;
import cz.cvut.fel.pjv.pieces.PieceType;
import cz.cvut.fel.pjv.pieces.Pieces;

import java.util.Iterator;
import java.util.logging.Level;

public class BoardModel {

    private final int BOARD_DIMENSION = 8;

    private final Spot[][] arimaaBoardSpot = new Spot[8][8];


    public BoardModel() {
        initSquares();
        initPieces();
        Game.logger.log(Level.CONFIG, "BoardModel was initiated.");
    }

    /**
     * Get Piece on coordination in Board
     * @param x [a, b, c, d, e, f, g, h]
     * @param y [1, 2, 3, 4, 5, 6, 7, 8]
     * @return  Piece
     */
    public Spot getSpot(char x, int y) {
        if (x < 'a' || x > 'h' || y < 1 || y > 8) {
            return null;
        } else {
            Game.logger.log(Level.FINE, "returning spot on position: " + x + " " + y);
            return arimaaBoardSpot[y-1][x-'a'];
        }
    }

    /**
     * Do move in BoardModel. Change position of Piece in its class.
     *
     * @param move valid Move to execute in BoardModel
     */
    public void doMove(Move move) {
        Spot originSquare = getSpot(move.getSx(), move.getSy());
        Spot destinationSquare = getSpot(move.getDx(), move.getDy());
        destinationSquare.setPiece(originSquare.getPiece());
        originSquare.setPiece(null);

    }

    /**
     * Undo last move in Model. Change position of Piece in its class.
     *
     * @param move from moveHistory
     */
    public void undoMove(Move move) {
        Spot originSpot = getSpot(move.getSx(), move.getSy());
        Spot destinationSpot = getSpot(move.getDx(), move.getDy());
        originSpot.setPiece(destinationSpot.getPiece());
        destinationSpot.setPiece(null);

    }

    /**
     * Save current to file.
     */
    public void saveState() {
        // TODO implement
    }

    /**
     * Load state of board from file.
     */
    public void loadState() {
        // TODO implement

    }


    private void initPieces() {
        // rabbit
        Iterator<Piece> silverRabbitsIterator = Pieces.getPieces(ColorPiece.SILVER, PieceType.RABBIT).iterator();
        Iterator<Piece> goldRabbitsIterator = Pieces.getPieces(ColorPiece.GOLD, PieceType.RABBIT).iterator();
        for (char col = 'a'; col <= 'h'; col++) {
            getSpot(col, 8).setPiece(silverRabbitsIterator.next());   // add gold Piece
            getSpot(col, 1).setPiece(goldRabbitsIterator.next());   // add silver Piece

        }

        // cat
        Iterator<Piece> silverCatIterator = Pieces.getPieces(ColorPiece.SILVER, PieceType.CAT).iterator();
        Iterator<Piece> goldCatIterator = Pieces.getPieces(ColorPiece.GOLD, PieceType.CAT).iterator();
        getSpot('a',7).setPiece(silverCatIterator.next());
        getSpot('h',7).setPiece(silverCatIterator.next());
        getSpot('a',2).setPiece(goldCatIterator.next());
        getSpot('h',2).setPiece(goldCatIterator.next());

        // dog
        Iterator<Piece> silverDogIterator = Pieces.getPieces(ColorPiece.SILVER, PieceType.DOG).iterator();
        Iterator<Piece> goldDogIterator = Pieces.getPieces(ColorPiece.GOLD, PieceType.DOG).iterator();
        getSpot('b',7).setPiece(silverDogIterator.next());
        getSpot('g',7).setPiece(silverDogIterator.next());
        getSpot('b',2).setPiece(goldDogIterator.next());
        getSpot('g',2).setPiece(goldDogIterator.next());

        // horse
        Iterator<Piece> silverHorseIterator = Pieces.getPieces(ColorPiece.SILVER, PieceType.HORSE).iterator();
        Iterator<Piece> goldHorseIterator = Pieces.getPieces(ColorPiece.GOLD, PieceType.HORSE).iterator();
        getSpot('c', 7).setPiece(silverHorseIterator.next());
        getSpot('f', 7).setPiece(silverHorseIterator.next());
        getSpot('c', 2).setPiece(goldHorseIterator.next());
        getSpot('f', 2).setPiece(goldHorseIterator.next());

        // camel
        Iterator<Piece> silverCamelIterator = Pieces.getPieces(ColorPiece.SILVER, PieceType.CAMEL).iterator();
        Iterator<Piece> goldCamelIterator = Pieces.getPieces(ColorPiece.GOLD, PieceType.CAMEL).iterator();
        getSpot('d', 7).setPiece(silverCamelIterator.next());
        getSpot('e', 2).setPiece(goldCamelIterator.next());

        // elephant
        Iterator<Piece> silverElephantIterator = Pieces.getPieces(ColorPiece.SILVER, PieceType.ELEPHANT).iterator();
        Iterator<Piece> goldElephantIterator = Pieces.getPieces(ColorPiece.GOLD, PieceType.ELEPHANT).iterator();
        getSpot('e', 7).setPiece(silverElephantIterator.next());
        getSpot('d', 2).setPiece(goldElephantIterator.next());


    }

    private void initSquares() {
        for (int i = 0; i < BOARD_DIMENSION; i++) {
            for (int j = 0; j < BOARD_DIMENSION; j++) {
                if ((i == 2 || i == 5) && (j == 2 || j == 5)) {
                    arimaaBoardSpot[i][j] = new Spot(i, j, TypeOfSpot.TRAP);
                } else {
                    arimaaBoardSpot[i][j] = new Spot(i, j, TypeOfSpot.NORMAL);
                }
            }
        }
    }

}
