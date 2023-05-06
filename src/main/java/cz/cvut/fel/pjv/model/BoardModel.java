package cz.cvut.fel.pjv.model;


import cz.cvut.fel.pjv.controller.Game;
import cz.cvut.fel.pjv.pieces.ColorPiece;
import cz.cvut.fel.pjv.pieces.Piece;
import cz.cvut.fel.pjv.pieces.PieceType;
import cz.cvut.fel.pjv.pieces.PieceSet;

import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;

public class BoardModel {

    private final int BOARD_DIMENSION = 8;
    private final Spot[][] arimaaBoardSpots = new Spot[8][8];
    private Game game = null;

    private Iterator<Piece> silverRabbitsIterator = PieceSet.getPieces(ColorPiece.SILVER, PieceType.RABBIT).iterator();
    private Iterator<Piece> goldRabbitsIterator = PieceSet.getPieces(ColorPiece.GOLD, PieceType.RABBIT).iterator();
    private Iterator<Piece> silverCatIterator = PieceSet.getPieces(ColorPiece.SILVER, PieceType.CAT).iterator();
    private Iterator<Piece> goldCatIterator = PieceSet.getPieces(ColorPiece.GOLD, PieceType.CAT).iterator();
    private Iterator<Piece> silverDogIterator = PieceSet.getPieces(ColorPiece.SILVER, PieceType.DOG).iterator();
    private Iterator<Piece> goldDogIterator = PieceSet.getPieces(ColorPiece.GOLD, PieceType.DOG).iterator();
    private Iterator<Piece> silverHorseIterator = PieceSet.getPieces(ColorPiece.SILVER, PieceType.HORSE).iterator();
    private Iterator<Piece> goldHorseIterator = PieceSet.getPieces(ColorPiece.GOLD, PieceType.HORSE).iterator();
    private Iterator<Piece> silverCamelIterator = PieceSet.getPieces(ColorPiece.SILVER, PieceType.CAMEL).iterator();
    private Iterator<Piece> goldCamelIterator = PieceSet.getPieces(ColorPiece.GOLD, PieceType.CAMEL).iterator();
    private Iterator<Piece> silverElephantIterator = PieceSet.getPieces(ColorPiece.SILVER, PieceType.ELEPHANT).iterator();
    private Iterator<Piece> goldElephantIterator = PieceSet.getPieces(ColorPiece.GOLD, PieceType.ELEPHANT).iterator();


    public BoardModel(Game game) {
        this.game = game;
        initSquares();
        initPieces();
        Game.logger.log(Level.CONFIG, "BoardModel was initiated.");
    }

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
        Game.logger.log(Level.FINER, x+" "+y);
        if (x < 'a' || x > 'h' || y < 1 || y > 8) {
            return null;
        } else {
            return arimaaBoardSpots[y-1][x-'a'];
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
        destinationSquare.setPiece(move.getPiece());
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
        originSpot.setPiece(move.getPiece());
        destinationSpot.setPiece(null);

        // bring back killed pieces
        for (Map.Entry<String, Piece> entry : move.getKilledPieces().entrySet()) {
            if (entry.getValue() != move.getPiece()) {
                Spot square = getSpot(entry.getKey().charAt(0), Integer.parseInt(String.valueOf(entry.getKey().charAt(1))));
                square.setPiece(entry.getValue());
            }
        }

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

    /**
     * Remove Piece from boardModel.
     *
     * @param piece is piece to be removed from board
     * @param x column coordinate of piece
     * @param y row coordinate of piece
     */
    public void removePiece(Piece piece, char x, int y) {
        piece.setAlive(false);
        Spot spot = getSpot(x, y);
        spot.setPiece(null);
    }

    // -----------------------------------------------------
    // ------------- Create Board and Pieces ---------------
    // -----------------------------------------------------

    private void initPieces() {

        if (game != null && game.getOwnLayout()) {
            ownLayoutOfPieces();
        } else {
            defaultLayoutOfPieces();
        }
    }

    private void ownLayoutOfPieces() {
        Scanner sc = new Scanner(System.in);
        String[] goldLayout;
        goldLayout = sc.nextLine().split(" ");
        String[] silverLayout;
        silverLayout = sc.nextLine().split(" ");

        if (goldLayout[0].equals("1g") && silverLayout[0].equals("1s")) {
            try {
                char offset = '0';
                for (int i = 1; i < goldLayout.length; i++) {
                    getSpot(goldLayout[i].charAt(1), goldLayout[i].charAt(2) - offset)
                            .setPiece(getPieceFromSet(goldLayout[i].charAt(0)));
                    getSpot(silverLayout[i].charAt(1), silverLayout[i].charAt(2) - offset)
                            .setPiece(getPieceFromSet(silverLayout[i].charAt(0)));
                }
            } catch (Exception e) {
                Game.logger.log(Level.WARNING, "WRONG LAYOUT INPUT, USING DEFAULT");
                e.printStackTrace();
                defaultLayoutOfPieces();
            }

        } else {
            Game.logger.log(Level.WARNING, "WRONG LAYOUT INPUT, USING DEFAULT");
            defaultLayoutOfPieces();
        }
    }

    private Piece getPieceFromSet(char c) {
        return switch (c) {
            case 'r' -> silverRabbitsIterator.next();
            case 'c' -> silverCatIterator.next();
            case 'd' -> silverDogIterator.next();
            case 'h' -> silverHorseIterator.next();
            case 'm' -> silverCamelIterator.next();
            case 'e' -> silverElephantIterator.next();
            case 'R' -> goldRabbitsIterator.next();
            case 'C' -> goldCatIterator.next();
            case 'D' -> goldDogIterator.next();
            case 'H' -> goldHorseIterator.next();
            case 'E' -> goldElephantIterator.next();
            case 'M' -> goldCamelIterator.next();
            default -> null;
        };
    }

    private void defaultLayoutOfPieces() {

        // rabbit
        Iterator<Piece> silverRabbitsIterator = PieceSet.getPieces(ColorPiece.SILVER, PieceType.RABBIT).iterator();
        Iterator<Piece> goldRabbitsIterator = PieceSet.getPieces(ColorPiece.GOLD, PieceType.RABBIT).iterator();
        for (char col = 'a'; col <= 'h'; col++) {
            getSpot(col, 8).setPiece(silverRabbitsIterator.next());   // add gold Piece
            getSpot(col, 1).setPiece(goldRabbitsIterator.next());   // add silver Piece
        }

        // cat
        Iterator<Piece> silverCatIterator = PieceSet.getPieces(ColorPiece.SILVER, PieceType.CAT).iterator();
        Iterator<Piece> goldCatIterator = PieceSet.getPieces(ColorPiece.GOLD, PieceType.CAT).iterator();
        getSpot('a',7).setPiece(silverCatIterator.next());
        getSpot('h',7).setPiece(silverCatIterator.next());
        getSpot('a',2).setPiece(goldCatIterator.next());
        getSpot('h',2).setPiece(goldCatIterator.next());

        // dog
        Iterator<Piece> silverDogIterator = PieceSet.getPieces(ColorPiece.SILVER, PieceType.DOG).iterator();
        Iterator<Piece> goldDogIterator = PieceSet.getPieces(ColorPiece.GOLD, PieceType.DOG).iterator();
        getSpot('b',7).setPiece(silverDogIterator.next());
        getSpot('g',7).setPiece(silverDogIterator.next());
        getSpot('b',2).setPiece(goldDogIterator.next());
        getSpot('g',2).setPiece(goldDogIterator.next());

        // horse
        Iterator<Piece> silverHorseIterator = PieceSet.getPieces(ColorPiece.SILVER, PieceType.HORSE).iterator();
        Iterator<Piece> goldHorseIterator = PieceSet.getPieces(ColorPiece.GOLD, PieceType.HORSE).iterator();
        getSpot('c', 7).setPiece(silverHorseIterator.next());
        getSpot('f', 7).setPiece(silverHorseIterator.next());
        getSpot('c', 2).setPiece(goldHorseIterator.next());
        getSpot('f', 2).setPiece(goldHorseIterator.next());

        // camel
        Iterator<Piece> silverCamelIterator = PieceSet.getPieces(ColorPiece.SILVER, PieceType.CAMEL).iterator();
        Iterator<Piece> goldCamelIterator = PieceSet.getPieces(ColorPiece.GOLD, PieceType.CAMEL).iterator();
        getSpot('d', 7).setPiece(silverCamelIterator.next());
        getSpot('e', 2).setPiece(goldCamelIterator.next());

        // elephant
        Iterator<Piece> silverElephantIterator = PieceSet.getPieces(ColorPiece.SILVER, PieceType.ELEPHANT).iterator();
        Iterator<Piece> goldElephantIterator = PieceSet.getPieces(ColorPiece.GOLD, PieceType.ELEPHANT).iterator();
        getSpot('e', 7).setPiece(silverElephantIterator.next());
        getSpot('d', 2).setPiece(goldElephantIterator.next());

    }

    private void initSquares() {
        for (int i = 0; i < BOARD_DIMENSION; i++) {
            for (int j = 0; j < BOARD_DIMENSION; j++) {
                if ((i == 2 || i == 5) && (j == 2 || j == 5)) {
                    arimaaBoardSpots[i][j] = new Spot(i, j, TypeOfSpot.TRAP);
                } else {
                    arimaaBoardSpots[i][j] = new Spot(i, j, TypeOfSpot.NORMAL);
                }
            }
        }
    }

    @Override
    public String toString() {
        // note! - StringBuilder is not thread safe
        char offset = 'a';
        StringBuilder str = new StringBuilder();
        str.append('\n');
        for (int i = BOARD_DIMENSION-1; i > -1; i--) {
            str.append(String.format("%d ", (i+1)));
            for (int j = 0; j < BOARD_DIMENSION; j++) {
                if (arimaaBoardSpots[i][j].getPiece() == null) {
                    str.append(String.format("[%s]", " "));
                } else {
                    str.append(String.format("[%s]", arimaaBoardSpots[i][j].getPiece().getNotationName()));
                }
            }
            str.append('\n');
        }
        str.append(String.format("%2s", " "));
        for (int i = 0; i < BOARD_DIMENSION; i++) {
            str.append(String.format(" %s ", (char)(((int)offset)+i)));
        }
        str.append('\n');
        return str.toString();
    }
}
