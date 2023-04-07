package cz.cvut.fel.pjv.model;

import java.util.Stack;
import java.util.logging.Level;

public class MoveLogger {

    private final Stack<Move> moveHistory;


    public MoveLogger() {
        this.moveHistory = new Stack<>();
    }

    /**
     * Save last made Move.
     */
    public void saveMove(Move move) {
        moveHistory.add(move);
    }

    /**
     * Pop last Move from Stack<Move>.
     */
    public Move undoMove() {
        Move move = null;
        if (moveHistory.isEmpty()) { Game.logger.log(Level.WARNING,"History of moves is empty!");
        } else { move = moveHistory.pop(); }
        return move;
    }
}
