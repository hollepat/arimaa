import cz.cvut.fel.pjv.controller.Game;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class GameTest {

    private InputStream defaultInputStream;

    @BeforeEach
    public void setUp() {
        defaultInputStream = System.in;
    }

    @AfterEach
    public void tearDown() {
        System.setIn(defaultInputStream);
    }

    @Test
    public void createGame() {
        Game game = new Game(true, 0, false);
    }
    @Test
    public void ownLayoutTest() {

    }

    @Test
    public void moveWrongPlayerTest() {
        Game game = new Game(true, 0, false);
        game.moveRequest('d', 2, 'd', 3);
        game.moveRequest('d', 3, 'd', 4);
        game.moveRequest('d', 4, 'd', 5);
        game.moveRequest('d', 5, 'e', 5);
        game.moveRequest('a', 2, 'a', 3); // fail
    }

    @Test
    public void moveTurnTest() {
        Game game = new Game(true, 0, false);
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
    public void trapTest() {
        Game game = new Game(true, 0, false);
        game.moveRequest('c', 2, 'c', 3);
        assert game.getBoardModel().getSpot('c', 3).getPiece() == null;
    }

}
