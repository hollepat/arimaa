import cz.cvut.fel.pjv.controller.Game;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class GameTest {

    private Game game;

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
        this.game = new Game(true, 0, false);
    }
    @Test
    public void trapTest() {
        this.game = new Game(true, 0, false);
        game.moveRequest('c', 2, 'c', 3);
        assert game.getBoardModel().getSpot('c', 3).getPiece() == null;
    }

    @Test
    public void ownLayoutTest() {
        String lay =  "1g Ra2 Rb2 Rc2 Rd2 Re2 Rf2 Rg2 Rh2 Ma1 Eb1 Dc1 Dd1 Ce1 Cf1 Hg1 Hh1" +
                "\n1s ra8 rb8 rc8 rd8 re8 rf8 rg8 rh8 ma7 eb7 dc7 dd7 ce7 cf7 hg7 hh7";
        System.setIn(new ByteArrayInputStream(lay.getBytes()));
        this.game = new Game(true, 0, true);

    }

}
