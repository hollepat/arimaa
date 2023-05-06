package cz.cvut.fel.pjv.model;

import java.util.LinkedList;
import java.util.List;

public class Turn {

    private List<Move> moves;
    private int number;

    public Turn(List<Move> moves) {
        this.moves = moves;
    }

    public void addMove(Move m) {
        moves.add(m);
    }

    public List<Move> getMoves() {
        return this.moves;
    }


}
