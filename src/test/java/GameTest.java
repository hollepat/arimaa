import cz.cvut.fel.pjv.model.EventHandler;
import cz.cvut.fel.pjv.model.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.util.concurrent.TimeUnit;

public class GameTest {

    Game game;


    @Test
    public void testGameCreation() {
        game = new Game();

    }

    @Test
    public void clickableImage() throws InterruptedException {      // version JButton not working
            JFrame f = new JFrame("My Icon Button");
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            JButton b = new JButton(new ImageIcon("/Users/patrikholler/workspace/pjv-sem/hollepat/arimaa/src/main/resources/images_of_pieces/Arimaa_cs.svg.png"));
            f.getContentPane().add(b);
            f.pack();
            f.setVisible(true);
            TimeUnit.SECONDS.sleep(10);

    }
}
