package cz.cvut.fel.pjv.model;


import java.util.Stack;

public class Board {    // Caretaker

    private PlayerGold gold;
    private PlayerSilver silver;
    private Originator originator;        // is current state of game (current snapshot)
    private Stack<Memento> history;

    /**
     * Create new snapshot of game, after change of game.
     */
    public void makeMove() {
        Memento m = originator.save();
        history.push(m);
    }

    public void undo() {
        Memento m = history.pop();
        originator.restore(m);
    }

    /**
     * Save current snapshot of game to file.
     */
    public void saveToFile() {
    }
}
