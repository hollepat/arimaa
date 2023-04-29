package cz.cvut.fel.pjv.model;

import cz.cvut.fel.pjv.pieces.ColorPiece;
import cz.cvut.fel.pjv.pieces.Piece;
import cz.cvut.fel.pjv.pieces.PieceType;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class GameValidator {


    private BoardModel boardModel;
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
        Game.logger.log(Level.CONFIG, "spot = " + spot.getX() + " " + spot.getY());

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

        List<Move> validMoves = new ArrayList<>();
        for (Move m : moves) {
            if (isValid(m)) {
                validMoves.add(m);
            }
        }

        return validMoves;
    }

    /**
     * Validate if Move can be played regarding the rules of Game.
     * @param move which has been just played
     * @return true == valid
     */
    public boolean isValid(Move move) {

        if (boardModel.getSpot(move.getDx(), move.getDy()).isOccupied()) {
            Game.logger.log(Level.WARNING,
                    "Spot " + move.getDx() + " " + move.getDy() + " is occupied by another Piece!");
            return false;
        }

        if (move.getPiece().getColor() == game.currentPlayer.getColor()
                && !isNextTo(move.getSx(), move.getSy(), move.getDx(), move.getDy())) {   // move my Piece
            return false;
        }

        if (!move.getPiece().isValidMove(move)) {
            Game.logger.log(Level.WARNING, "Rabbit cannot move backwards!");
            return false;
        }

        if (game.pushPromise) {     // promise to move own piece into place after push
            if (!isPushedByPiece(move)) {
                return false;
            }
        }

        if (move.getPiece().getColor() != game.currentPlayer.getColor()) {  // move opposite Piece
            if (!isDraggedByPiece(move)) {      // is enemy piece dragged?
                if (!isPushedByPiece(move)) {   // if not --> has to be pushed
                    return false;
                }
            }
        }

        // TODO check if Piece is on trap spot and can be saved or is doomed
        // TODO Piece is frozen if is near stronger enemy Piece and not have next to itself friendly Piece
        return true;
    }

    private boolean checkTrapPlaces() {
        // TODO
        return false;
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
        if (move.getPiece().getType() == PieceType.RABBIT && move.getPiece().getColor() == ColorPiece.GOLD && move.getDy() == 8) {
            game.setGameStatus(GameStatus.GOLD_WIN);
            Game.logger.log(Level.INFO, "Gold player wins!");
            return true;
        }

        if (move.getPiece().getType() == PieceType.RABBIT && move.getPiece().getColor() == ColorPiece.SILVER && move.getDy() == 1) {
            game.setGameStatus(GameStatus.SILVER_WIN);
            Game.logger.log(Level.INFO, "Silver player wins!");
            return true;
        }

        // TODO END GAME IF ONE PLAYER IS BLOCKED

        return false;
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

    private boolean isPushedByPiece(Move move) {
        Move previousMove = game.getMoveLogger().getLastMove();
        if (previousMove.pushPromise) {
             if (!(previousMove.getSx() == move.getDx() && previousMove.getSy() == move.getDy())) {
                 game.undoMove();   // undo also push move if promised move rejected
                 return false;
             }
             return true;
        } else {    // make promise for next move (move own piece on old spot of enemy piece
             if (game.movesInTurn > game.MAX_MOVES-1) {
                Game.logger.log(Level.WARNING, "Cannot proceed with push, you have " + game.movesInTurn + " move left!");
                return false;
            }
            if (isStrongerAround(move)) {
                Game.logger.log(Level.CONFIG,
                    "Created push promise!\n" +
                        "(In next move you have to drag your piece on spot of enemy piece you dragged before)\n" +
                         "(your piece has to be stronger than enemy ones)");
                move.pushPromise = true;
                return true;
            }
        }


        return false;
    }

    private char addX(char x, int d) {
        char newX = (char)((int)x + d);
        Game.logger.log(Level.FINE, x + " -> " + newX + " (moved by " + d + ")");
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
