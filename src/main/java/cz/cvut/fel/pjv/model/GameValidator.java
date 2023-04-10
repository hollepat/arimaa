package cz.cvut.fel.pjv.model;

import cz.cvut.fel.pjv.pieces.ColorPiece;
import cz.cvut.fel.pjv.pieces.Piece;
import cz.cvut.fel.pjv.pieces.PieceType;

import java.util.logging.Level;

public class GameValidator {


    private BoardModel boardModel;
    private final Game game;

    public final int ZERO_MOVES = 0;
    public final int MAX_MOVES = 3;

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
     * Validate if Move can be played regarding the rules of Game.
     * @param move which has been just played
     * @return true == valid
     */
    public boolean validateMove(Move move) {

        if (boardModel.getSpot(move.getDx(), move.getDy()).isOccupied()) {
            Game.logger.log(Level.WARNING, "Spot is occupied by another Piece!");
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
            if (!isDraggedByPiece(move)) {
                if (!isPushedByPiece(move)) {   // has to be pushed
                    return false;
                }
            }
        }

        // TODO check if Piece is on trap spot and can be saved or is doomed
        // TODO Piece is frozen if is near stronger enemy Piece and not have next to itself friendly Piece

        checkMovesInTurn();
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

    private void checkMovesInTurn() {
        if (game.movesInTurn == MAX_MOVES) {
            game.switchCurrentPlayer();
            game.movesInTurn = ZERO_MOVES;
        } else {
            game.movesInTurn++;
        }
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
        if (game.pushPromise) {
            Move previousMove = game.getMoveLogger().getLastMove();
             if (!(previousMove.getSx() == move.getDx() && previousMove.getSy() == move.getDy())) {
                 game.undoMove();   // undo push move if promise rejected
                 game.pushPromise = false;
                 return false;
             }
             game.pushPromise = false;
             return true;
        } else {    // make promise for next move
             if (game.movesInTurn > MAX_MOVES-1) {
                Game.logger.log(Level.WARNING, "Cannot proceed with push, because you have no moves left to move your Piece!");
                return false;
            }
            if (isStrongerAround(move)) {
                Game.logger.log(Level.CONFIG, "Created push promise!");
                game.pushPromise = true;
                return true;
            }
        }


        return false;
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
