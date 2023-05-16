import cz.cvut.fel.pjv.controller.Game;
import cz.cvut.fel.pjv.controller.GameStatus;
import cz.cvut.fel.pjv.model.Move;
import cz.cvut.fel.pjv.pieces.ColorPiece;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;


public class GameTest {

    @Test
    public void createGame() {
        Game game = new Game(true, 0, false);
    }

    @Test
    public void moveFailTooManyMovesTest() {
        Game game = new Game(true, 0, false);
        game.moveRequest('d', 2, 'd', 3);
        game.moveRequest('d', 3, 'd', 4);
        game.moveRequest('d', 4, 'd', 5);
        game.moveRequest('d', 5, 'e', 5);
        game.moveRequest('a', 2, 'a', 3); // fail

        assert game.getBoardModel().getSpot('a', 3).getPiece() == null;
    }

    @Test
    public void moveTurnTest() {
        Game game = new Game(true, 0, false);
        // ACTIVATE GAME
        game.setGameStatus(GameStatus.ACTIVE);
        // GOLD
        game.moveRequest('d', 2, 'd', 3);
        game.moveRequest('d', 3, 'd', 4);
        game.moveRequest('d', 4, 'd', 5);
        game.moveRequest('d', 5, 'e', 5);
        // SILVER
        game.moveRequest('a', 7, 'a', 6);
        game.moveRequest('e', 7, 'e', 6);
        game.endTurn();
        // GOLD
        game.moveRequest('e', 6, 'f',6);
        game.moveRequest('e', 5, 'e',6);
        game.moveRequest('f', 6, 'g',6);
        game.moveRequest('b', 2, 'b',3);
        game.moveRequest('h', 7, 'h', 6);
        game.endTurn();
        // SILVER
        game.moveRequest('d', 7, 'd', 6);
        game.moveRequest('d', 6, 'd', 5);
        game.endTurn();
        // GOLD
        game.moveRequest('d', 5, 'c', 5);
        game.moveRequest('h', 2, 'h', 3);   // push fail
        game.moveRequest('d', 5, 'c', 5);
        game.moveRequest('e', 5, 'd', 5);
    }

    @Test
    public void undoTurnTest() {
        Game game = new Game(true, 0, false);
        // ACTIVATE GAME
        game.setGameStatus(GameStatus.ACTIVE);
        // GOLD
        game.moveRequest('d', 2, 'd', 3);
        game.moveRequest('d', 3, 'd', 4);
        game.moveRequest('d', 4, 'd', 5);
        game.moveRequest('d', 5, 'e', 5);
        // SILVER
        game.moveRequest('a', 7, 'a', 6);
        game.moveRequest('e', 7, 'e', 6);
        game.endTurn();
        // GOLD
        game.moveRequest('h', 2, 'h', 3);
        game.moveRequest('h', 3, 'h', 4);

        game.undoTurn();
    }

    @Test
    public void trapSpotTest() {
        Game game = new Game(true, 0, false);
        game.moveRequest('c', 2, 'c', 3);
        assert game.getBoardModel().getSpot('c', 3).getPiece() == null;
    }

    @Test
    public void generateMovesNPCTest() {
        Game game = new Game(true, 0, false);
        List<Move> moves = game.getMoveValidator().generatePossibleMoves(ColorPiece.GOLD);
        System.out.println(moves);
        assert !moves.isEmpty();
    }

    @Test
    public void npcPlayingTest() {
        Game game = new Game(true, 0, false, false,  true);
        Random random = new Random();
        for(int i = 0; i<1000; i++) {
            // player make one move
            List<Move> randomMoves = game.getMoveValidator().generatePossibleMoves(game.getCurrentPlayer().getColor());
            Move m = randomMoves.get(random.nextInt(randomMoves.size()-1));
            game.moveRequest(m.getSx(), m.getSy(), m.getDx(), m.getDy());
            // npc player make some moves
            game.moveRequestPC();
        }
    }
}
