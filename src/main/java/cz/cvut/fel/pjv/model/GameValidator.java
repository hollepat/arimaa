package cz.cvut.fel.pjv.model;

import cz.cvut.fel.pjv.pieces.ColorPiece;
import cz.cvut.fel.pjv.pieces.Piece;
import cz.cvut.fel.pjv.pieces.PieceSet;
import cz.cvut.fel.pjv.pieces.PieceType;

import java.awt.font.GlyphMetrics;
import java.beans.Introspector;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;

import static cz.cvut.fel.pjv.pieces.ColorPiece.GOLD;
import static cz.cvut.fel.pjv.pieces.ColorPiece.SILVER;

public class GameValidator {


    private final BoardModel boardModel;
    private final Game game;

    /**
     * Constructor.
     *
     * @param boardModel is model of Board
     * @param game object using GameValidator to validate moves.
     */
    public GameValidator(BoardModel boardModel, Game game) {
        this.boardModel = boardModel;
        this.game = game;
    }

    /**
     * Generate all valid moves for pctbm by current player
     * @param pctbm pieceToBeMoved
     * @param player player moving piece
     * @return List of valid moves for Piece
     */
    public List<Move> generateValidMoves(Piece pctbm, Spot spot, Player player) {
        Game.logger.log(Level.INFO, "Generating moves for piece on " + spot.getX() + " " + spot.getY());

        List<Move> moves = new ArrayList<>();
        if (spot.getX() != 'h') {
            Move moveUp = new Move(pctbm, spot.getX(), spot.getY(), addX(spot.getX(), 1), spot.getY(), player);
            moves.add(moveUp);
        }
        if (spot.getX() != 'a') {
            Move moveDown = new Move(pctbm, spot.getX(), spot.getY(), addX(spot.getX(), -1), spot.getY(), player);
            moves.add(moveDown);
        }
        if (spot.getY() < 8) {
            Move moveLeft = new Move(pctbm, spot.getX(), spot.getY(), spot.getX(), spot.getY() + 1, player);
            moves.add(moveLeft);
        }
        if (spot.getY() > 1) {
            Move moveRight = new Move(pctbm, spot.getX(), spot.getY(), spot.getX(), spot.getY() - 1, player);
            moves.add(moveRight);
        }
        Game.logger.log(Level.CONFIG, "Moves: " + moves.toString());
        List<Move> validMoves = moves.stream().filter(move -> isValid(move)).collect(Collectors.toList());
        Game.logger.log(Level.CONFIG,"Valid move: " + validMoves.toString());
        return validMoves;
    }

    /**
     * Validate if Move can be played regarding the rules of Game.
     * @param move which has been just played
     * @return true == valid
     */
    public boolean isValid(Move move) {

        // check if spot is free
        if (boardModel.getSpot(move.getDx(), move.getDy()).isOccupied()) {
            Game.logger.log(Level.WARNING,
                    "Spot " + move.getDx() + " " + move.getDy() + " is occupied by another Piece!");
            return false;
        }

        // check if not moving enemy piece (exception push and drag)
        if (move.getPiece().getColor() == game.currentPlayer.getColor()
                && !isNextTo(move.getSx(), move.getSy(), move.getDx(), move.getDy())) {
            return false;
        }

        // check if piece itself allows this move
        if (!move.getPiece().isValidMove(move)) {
            Game.logger.log(Level.WARNING, "Rabbit cannot move backwards!");
            return false;
        }

        // check for push promise
        Move previousMove = game.getMoveLogger().getLastMove();
        if (previousMove != null && previousMove.pushPromise) {     // promise to move own piece into place after push
            if (!keepingPromise(move)) { return false; }
        }

        // check if move is push or drag
        if (move.getPiece().getColor() != game.currentPlayer.getColor()) {  // move opposite Piece
            if (!isDraggedByPiece(move)) {      // is enemy piece dragged?
                return isPushedByPiece(move);   // if not --> has to be pushed
            }
        }

        // TODO Piece is frozen if is near stronger enemy Piece and not have next to itself friendly Piece

        return true;
    }

    public void checkTrapped(Move move){

        try {
            if (!isFriendlyAround('c', 3)) {
                Game.logger.log(Level.CONFIG, boardModel.getSpot('c', 3).getPiece().toString() + " is trapped!");
                move.addKilledPiece('c' + Integer.toString(3), boardModel.getSpot('c', 3).getPiece());
            }
        } catch (NullPointerException e) {}
        try {
            if (!isFriendlyAround('c', 6)) {
                Game.logger.log(Level.CONFIG, boardModel.getSpot('c', 6).getPiece().toString() + " is trapped!");
                move.addKilledPiece('c' + Integer.toString(6), boardModel.getSpot('c', 6).getPiece());
            }
        } catch (NullPointerException e) {}
        try {
            if (!isFriendlyAround('f', 3)) {
                Game.logger.log(Level.CONFIG, boardModel.getSpot('f', 3).getPiece().toString() + " is trapped!");
                move.addKilledPiece('f' + Integer.toString(3), boardModel.getSpot('f', 3).getPiece());
            }
        } catch (NullPointerException e) {}
        try {
            if (!isFriendlyAround('f', 6)) {
                Game.logger.log(Level.CONFIG, boardModel.getSpot('f', 6).getPiece().toString() + " is trapped!");
                move.addKilledPiece('f' + Integer.toString(6), boardModel.getSpot('f', 6).getPiece());
            }
        } catch (NullPointerException e) {}

    }


    private boolean isPieceFrozen() {
        // TODO
        return false;
    }


    /**
     * Check if move is not winning move.
     * @param move currently made valid move
     * @return true if move is winning
     */
    public boolean endMove(Move move) {
        if (move.getPiece().getType() == PieceType.RABBIT && move.getPiece().getColor() == GOLD && move.getDy() == 8) {
            game.setGameStatus(GameStatus.GOLD_WIN);
            Game.logger.log(Level.INFO, "Gold player wins!");
            return true;
        }

        if (move.getPiece().getType() == PieceType.RABBIT && move.getPiece().getColor() == ColorPiece.SILVER && move.getDy() == 1) {
            game.setGameStatus(GameStatus.SILVER_WIN);
            Game.logger.log(Level.INFO, "Silver player wins!");
            return true;
        }

        if (hasDeadPieces(GOLD)) {
            Game.logger.log(Level.INFO, "All " + SILVER + " pieces are dead!");
            return true;
        }
        if (hasDeadPieces(SILVER)) {
            Game.logger.log(Level.INFO, "All " + GOLD + " pieces are dead!");
            return true;
        }

        return false;
    }

    private boolean hasDeadPieces(ColorPiece colorPiece) {
        List<Piece> alivePieces = PieceSet.getPieces(colorPiece).stream()
                .filter(Piece::isAlive).toList();
        return alivePieces.isEmpty();
    }

    private boolean isNextTo(char sx1, int sy1, char sx2, int sy2) {
        int moveHorizontal = Math.abs(sx1 - sx2);
        int moveVertical = Math.abs(sy1 - sy2);

        if (moveHorizontal == 0 && moveVertical == 1) { return true; }
        if (moveHorizontal == 1 && moveVertical == 0) { return true; }
        return false;
    }

    private boolean isStronger(Piece stronger, Piece weaker) {
        if (stronger == null || weaker == null) {
            return false;
        }
        if (stronger.getColor() == game.currentPlayer.getColor() && stronger.getPieceStrength() > weaker.getPieceStrength()) {
            return true;
        }
        return false;
    }

    private boolean isStrongerAround(Move move) {
        if (isStronger(boardModel.getSpot(move.getSx(), move.getSy()-1).getPiece() , move.getPiece())) { return true; }
        if (isStronger(boardModel.getSpot(move.getSx(), move.getSy()+1).getPiece() , move.getPiece())) { return true; }
        if (isStronger(boardModel.getSpot((char)((int)move.getSx() + 1), move.getSy()).getPiece() , move.getPiece())) { return true; }
        if (isStronger(boardModel.getSpot((char)((int)move.getSx() - 1), move.getSy()).getPiece() , move.getPiece())) { return true; }
        return false;
    }

    private boolean isFriendlyAround(char x, int y) {
        Piece piece = boardModel.getSpot(x, y).getPiece();
        try {
            if (boardModel.getSpot(x, y-1).getPiece().getColor() == piece.getColor()) {
                Game.logger.log(Level.CONFIG, piece.toString() + " is saved!");
                return true;
            }
        } catch (NullPointerException e) {
            Game.logger.log(Level.FINER, e.getMessage());
        }
        try {
            if (boardModel.getSpot(x, y+1).getPiece().getColor() == piece.getColor()) {
                Game.logger.log(Level.CONFIG, piece.toString() + " is saved!");
                return true;
            }
        } catch (NullPointerException e) {
            Game.logger.log(Level.FINER, e.getMessage());
        }
        try {
            if (boardModel.getSpot(addX(x, 1), y).getPiece().getColor()  == piece.getColor()) {
                Game.logger.log(Level.CONFIG, piece.toString() + " is saved!");
                return true;
            }
        } catch (NullPointerException e) {
            Game.logger.log(Level.FINER, e.getMessage());
        }
        try {
            if (boardModel.getSpot(addX(x, - 1), y).getPiece().getColor()  == piece.getColor()) {
                Game.logger.log(Level.CONFIG, piece.toString() + " is saved!");
                return true;
            }
        } catch (NullPointerException e) {
            Game.logger.log(Level.FINER, e.getMessage());
        }
        return false;
    }

    private boolean isDraggedByPiece(Move move) {
        Move previousMove = game.getMoveLogger().getLastMove();
        if (previousMove == null) {
            return false;
        }
        if (move.getDx() == previousMove.getSx() && move.getDy() == previousMove.getSy()
                && isNextTo(previousMove.getSx(), previousMove.getSy(), move.getSx(), move.getSy())
                && isStronger(previousMove.getPiece(), move.getPiece())) {
            return true;
        }
        return false;
    }

    private boolean keepingPromise(Move move) {
        Move previousMove = game.getMoveLogger().getLastMove();
        if (previousMove.getSx() == move.getDx() && previousMove.getSy() == move.getDy()) {
            return true;
        }
        return false;
    }

    private boolean isPushedByPiece(Move move) {
         if (game.movesInTurn > game.MAX_MOVES-1) {
            Game.logger.log(Level.WARNING, "Cannot proceed with push, you have " + game.movesInTurn + " move left!");
            return false;
        }
        if (isStrongerAround(move)) {
            Game.logger.log(Level.CONFIG,
                    """
                            Created push promise!
                            (In next move you have to drag your piece on spot of enemy piece you dragged before)
                            (your piece has to be stronger than enemy ones)""");
            move.pushPromise = true;
            return true;
        }
        return false;
    }

    private char addX(char x, int d) {
        char newX = (char)((int)x + d);
        return newX;
    }

    /**
     * Check if custom layout of Pieces is valid.
     * @return boolean
     */
    public boolean checkValidLayout() {
        // TODO implement
        return true;
    }

}
