package cz.cvut.fel.pjv.model;

import java.util.logging.Level;

public class GameValidator {


    private BoardModel boardModel;

    public GameValidator(BoardModel boardModel) {
        this.boardModel = boardModel;
    }

    public boolean validateMove(Move move) {

        // TODO check if player not moved Piece ove other Piece
        if (boardModel.getSpot(move.getDx(), move.getDy()).isOccupied()) {
            Game.logger.log(Level.WARNING, "Spot is occupied by another Piece!");
            return false;
        }

        // TODO check if current player can move this Piece

        return true;
    }

}
