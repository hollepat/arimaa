package cz.cvut.fel.pjv.model;

import javafx.scene.layout.GridPane;

public class Board {   // is Originator that holds it's current state

    private State currentState;
    private PlayerGold gold;
    private PlayerSilver silver;
    private GridPane chessBoard;

    /**
     * Constructor for Board
     *
     * @param chessBoard chessBoard GridPane rendering the board state
     */
    public Board(GridPane chessBoard) {
        this.currentState = new State();
        // TODO this.gold = gold;
        // TODO this.silver = silver;
        this.chessBoard = chessBoard;
    }

    /**
     * Save current State in Memento
     *
     * @return Memento containing the State
     */
    public Memento save() {
        Memento memento = new Memento(currentState);
        return memento;
    }

    /**
     * Restore previous State from Memento at top of the Stack
     *
     * @param memento Memento containing the State chessBoard will change to
     */
    public void restore(Memento memento) {
        this.currentState = memento.getState();
    }

}
