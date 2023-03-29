package cz.cvut.fel.pjv.model;

import java.util.Stack;

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
     * Undo first Move in Stack<Move>.
     */
    public Move undoMove() {
        Move move = null;
        if (moveHistory.isEmpty()) { System.out.println("History of moves is empty!");
        } else { move = moveHistory.pop(); }
        return move;
    }
}
