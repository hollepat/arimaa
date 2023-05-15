package cz.cvut.fel.pjv.model;

import java.util.ArrayList;
import java.util.List;

public class Turn {

    private final List<Move> moves;
    private final Player player;
    private final int cnt;

    /**
     * Constructor for Turn.
     * @param player which is making turn
     */
    public Turn(Player player) {
        this.player = player;
        cnt = player.getTurn();
        moves = new ArrayList<>();
    }

    /**
     * Add move made in this turn.
     * @param m is Move object
     */
    public void addMove(Move m) {
        moves.add(m);
    }

    /**
     * Remove move from Turn.
     */
    public void popMove() {
        moves.remove(moves.size()-1);
    }

    public List<Move> getMoves() {
        return this.moves;
    }

    public Player getPlayer() {
        return player;
    }

    /**
     * Get number of Turn.
     * @return number of Turn
     */
    public int getCnt() {
        return cnt;
    }

    /**
     * Describes Turn in arimaa notation.
     * @return String describing Turn in arimaa notation
     */
    public String getNotation() {
        StringBuilder notation = new StringBuilder(Integer.toString(cnt) + player.getNotation());
        for (Move m : moves) {
            notation.append(" ").append(m.getNotation());
        }
        return notation.toString();
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
