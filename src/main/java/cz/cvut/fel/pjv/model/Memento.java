package cz.cvut.fel.pjv.model;


public class Memento {      // snapshot of game

    private final Move move;

    public Memento(Move move) {
        this.move = move;
    }

    public Move getMove() {
        return move;
    }

}
