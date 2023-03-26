import cz.cvut.fel.pjv.model.ArimaaBoard;
import cz.cvut.fel.pjv.model.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.util.concurrent.TimeUnit;

public class GameTest {

    ArimaaBoard arimaaBoard;
    Game game;

    @BeforeEach
    public void setUp() {
        game = new Game();
        arimaaBoard = new ArimaaBoard();
    }

    @Test
    public void testGameCreation() {
        game.startGame();
    }

    @Test
    public void clickableImage() throws InterruptedException {
            JFrame f = new JFrame("My Icon Button");
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            JButton b = new JButton(new ImageIcon("/Users/patrikholler/workspace/pjv-sem/hollepat/arimaa/src/main/resources/images_of_pieces/Arimaa_cs.svg.png"));
            f.getContentPane().add(b);
            f.pack();
            f.setVisible(true);
            TimeUnit.SECONDS.sleep(10);

    }
}
