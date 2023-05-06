package cz.cvut.fel.pjv.model;

import cz.cvut.fel.pjv.controller.Game;

import java.io.File;
import java.util.LinkedList;
import java.util.Stack;
import java.util.logging.Level;

public class MoveLogger {

    private final Stack<Move> moveHistory;
    private final LinkedList<Turn> turnHistory;

    /**
     * Constructor.
     */
    public MoveLogger() {
        this.moveHistory = new Stack<>();
        this.turnHistory = new LinkedList<>();
    }

    /**
     * Save last made Move.
     */
    public void saveMove(Move move) {
        moveHistory.add(move);
    }

    public void updateTurn(Move move) {

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

    /**
     * @return last move from Stack<Move>
     */
    public Move getLastMove() {
        if (moveHistory.isEmpty()) { return null; }
        return moveHistory.peek();
    }

    public void saveMoves(File file) {

    }
}
