package cz.cvut.fel.pjv.model;

import cz.cvut.fel.pjv.controller.Game;

import java.io.File;
import java.util.LinkedList;
import java.util.Stack;
import java.util.logging.Level;

public class MoveLogger {

    private Game game;
    private final Stack<Move> moveHistory;
    private final LinkedList<Turn> turnHistory;



    /**
     * Constructor.
     */
    public MoveLogger(Game game) {
        this.game = game;
        this.moveHistory = new Stack<>();
        this.turnHistory = new LinkedList<>();
    }

    /**
     * Save last made Turn.
     */
    public void saveTurn(Turn turn) {
        turnHistory.add(turn);
        Game.logger.log(Level.FINE, "Turn: " + turn.toString());
    }

    /**
     * Pop last Turn from LinkedList<Turn>.
     */
    public Turn popTurn() {
        Turn turn = null;
        if (moveHistory.isEmpty()) { Game.logger.log(Level.WARNING,"History of moves is empty!");
        } else { turn = turnHistory.removeLast(); }
        return turn;
    }

    /**
     * @return last turn from LinkedList<Turn>
     */
    public Turn peekTurn() {
        if (turnHistory.isEmpty()) { return null; }
        return turnHistory.peek();
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
    public Move popMove() {
        Move move = null;
        if (moveHistory.isEmpty()) { Game.logger.log(Level.WARNING,"History of moves is empty!");
        } else { move = moveHistory.pop(); }
        return move;
    }
    /**
     * @return last move from Stack<Move>
     */
    public Move peekMove() {
        if (moveHistory.isEmpty()) { return null; }
        return moveHistory.peek();
    }

    /**
     *
     * @return true if no Move is logged
     */
    public Boolean isEmpty() {
        return moveHistory.isEmpty();
    }


    /**
     * Write to file all Turns.
     * @param file to write game history
     */
    public void saveMoves(File file) {
        // TODO implement
    }
}
