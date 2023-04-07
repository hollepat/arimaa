package cz.cvut.fel.pjv.model;

import cz.cvut.fel.pjv.pieces.ColorPiece;
import cz.cvut.fel.pjv.pieces.PieceType;

import java.util.logging.Level;

public class GameValidator {


    private BoardModel boardModel;
    private final Game game;

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

        if (!move.getPiece().isValidMove(move)) {
            Game.logger.log(Level.WARNING, "Rabbit cannot move backwards!");
            return false;
        }

        if (move.getPiece().getColor() == game.currentPlayer.getColor() && !isMoveToNextSpot(move)) {   // move my Piece
            // TODO check if move is by one Spot
            return false;
        }

        if (move.getPiece().getColor() != game.currentPlayer.getColor()) {  // move opposite Piece
            return false;
            // TODO check if move is push or drag based on last move
        }

        // TODO check if current player can move this Piece based on currentPlayer and last moved Piece (drag or push enemy Piece)


        // TODO Player has only 4 moves per turn, but has to make at least one

        // TODO check if Piece is on trap spot and can be saved or is doomed!!! :(

        // TODO Piece is frozen if is near stronger enemy Piece and not have next to itself friendly Piece


        return true;
    }

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

    private boolean isMoveToNextSpot(Move move) {
        System.out.println(Math.abs(move.getSx() - move.getDx()));
        if (Math.abs(move.getSy() - move.getDy()) > 1 || Math.abs(move.getSx() - move.getDx()) > 1) {
            return false;
        }
        return true;
    }

}
