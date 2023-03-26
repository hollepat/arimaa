package cz.cvut.fel.pjv.model;


public class Memento {      // snapshot of game

    private final State state;

    public Memento(State state) {
        this.state = state;
    }

    public State getState() {

        return null;
    }

    public void writeToFile() {
        // write to file current state
    }
}
