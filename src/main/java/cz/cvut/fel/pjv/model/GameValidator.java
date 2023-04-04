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

        // TODO check if current player can move this Piece


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

        return false;
    }

}
