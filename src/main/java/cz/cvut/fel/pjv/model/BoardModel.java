package cz.cvut.fel.pjv.model;


import cz.cvut.fel.pjv.controller.Game;
import cz.cvut.fel.pjv.pieces.ColorPiece;
import cz.cvut.fel.pjv.pieces.Piece;
import cz.cvut.fel.pjv.pieces.PieceType;
import cz.cvut.fel.pjv.pieces.PieceSet;

import java.io.File;
import java.util.*;
import java.util.logging.Level;

public class BoardModel {

    public final int BOARD_DIMENSION = 8;
    private final Spot[][] arimaaBoardSpots = new Spot[8][8];
    private Game game = null;

    private String defaultLayoutGold = "1g Ra1 Rb1 Rc1 Rd1 Re1 Rf1 Rg1 Rh1 Ma2 Eb2 Dc2 Dd2 Ce2 Cf2 Hg2 Hh2";
    private String defaultLayoutSilver = "1s ra8 rb8 rc8 rd8 re8 rf8 rg8 rh8 ma7 eb7 dc7 dd7 ce7 cf7 hg7 hh7";

    private String currentLayoutGold;
    private String currentLayoutSilver;
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


    /**
     * Constructor for boardModel when creating New Game.
     * @param game is New Game
     */
    public BoardModel(Game game) {
        this.game = game;
        initSquares();
        initPieces();
        Game.logger.log(Level.CONFIG, "BoardModel was initiated.");
    }

    /**
     * Constructor for boardModel when recreating Game from File.
     * @param game
     * @param layoutGold
     * @param layoutSilver
     */
    public BoardModel(Game game, String layoutGold, String layoutSilver) {
        this.game = game;
        initSquares();
        // set own layout of Pieces
        setLayout(layoutGold.split(" "), layoutSilver.split(" "));
        Game.logger.log(Level.CONFIG, "BoardModel was initiated.");
    }

    /**
     * Constructor to create only boardModel.
     */
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
    public void saveState(File file) {
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
            setOwnLayoutOfPieces();
        } else {
            setLayout(defaultLayoutGold.split(" "), defaultLayoutSilver.split(" "));
        }
    }

    private void setOwnLayoutOfPieces() {
        Scanner sc = new Scanner(System.in);
        String[] goldLayout;
        currentLayoutGold = sc.nextLine();
        goldLayout = currentLayoutGold.split(" ");
        String[] silverLayout;
        currentLayoutSilver = sc.nextLine();
        silverLayout = currentLayoutSilver.split(" ");

        if (goldLayout[0].equals("1g") && silverLayout[0].equals("1s")) {
            setLayout(goldLayout, silverLayout);
        } else {
            Game.logger.log(Level.WARNING, "WRONG LAYOUT INPUT, USING DEFAULT");
            setLayout(defaultLayoutGold.split(" "), defaultLayoutSilver.split(" "));
        }
    }

    private void setLayout(String[] goldLayout, String[] silverLayout) {
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
            setLayout(defaultLayoutGold.split(" "), defaultLayoutSilver.split(" "));
        }
        currentLayoutGold = defaultLayoutGold;
        currentLayoutSilver = defaultLayoutSilver;

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

    /**
     * Get all Spots where stands Piece, which has specified color.
     * @param color color of Pieces to look for
     * @return list of Spot with Piece of specified color
     */
    public List<Spot> getPieces(ColorPiece color) {
        List<Spot> list = new ArrayList<>();
        for (int i = 0; i < BOARD_DIMENSION; i++) {
            for (int j = 0; j < BOARD_DIMENSION; j++) {
                Piece p = arimaaBoardSpots[i][j].getPiece();
                if (p != null && p.getColor() == color) {
                    list.add(arimaaBoardSpots[i][j]);
                }
            }
        }
        return  list;
    }

    public String getCurrentLayoutGold() {
        return currentLayoutGold;
    }

    public String getCurrentLayoutSilver() {
        return currentLayoutSilver;
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
