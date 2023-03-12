package cz.cvut.fel.pjv.model;


import javax.swing.*;

public class ArimaaBoard {
    // is Originator that holds it's current state
    // TODO draw state of board to GUI

    private State currentState;
    private PlayerGold gold;
    private PlayerSilver silver;
    private Spot[][] chessBoard;



    /**
     * Constructor for Board.
     */
    public ArimaaBoard() {
        this.currentState = new State();
        // TODO this.gold = gold;
        // TODO this.silver = silver;
        this.chessBoard = new Spot[8][8];
    }

    /**
     * Save current State in Memento.
     *
     * @return Memento containing the State
     */
    public Memento save() {
        Memento memento = new Memento(currentState);
        return memento;
    }

    /**
     * Restore previous State from Memento at top of the Stack.
     *
     * @param memento Memento containing the State chessBoard will change to
     */
    public void restore(Memento memento) {
        this.currentState = memento.getState();
    }

}
