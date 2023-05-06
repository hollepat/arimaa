package cz.cvut.fel.pjv.model;

import java.util.ArrayList;
import java.util.List;

public class Turn {

    private List<Move> moves;

    private Player player;
    private int cnt;


    public Turn(Player player) {
        this.player = player;
        cnt = player.getTurn();
        moves = new ArrayList<>();
    }

    public void addMove(Move m) {
        moves.add(m);
    }

    public void popMove() {
        moves.remove(moves.size()-1);
    }

    public List<Move> getMoves() {
        return this.moves;
    }

    public Player getPlayer() {
        return player;
    }

    public int getCnt() {
        return cnt;
    }

    public String getNotation() {
        String notation = Integer.toString(cnt) + player.getNotation();
        for (Move m : moves) {
            notation += " " + m.getNotation();
        }
        return notation;
    }

    @Override
    public String toString() {
        return "Turn{" +
                "moves=" + moves +
                ", player=" + player +
                ", cnt=" + cnt +
                '}';
    }
}
