import cz.cvut.fel.pjv.model.BoardModel;
import cz.cvut.fel.pjv.model.TypeOfSpot;
import cz.cvut.fel.pjv.pieces.Cat;
import cz.cvut.fel.pjv.pieces.Piece;
import cz.cvut.fel.pjv.pieces.Rabbit;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.Callable;

public class BoardModelTest {

    BoardModel boardModel;

    @BeforeEach
    public void setUp() {
        boardModel = new BoardModel();
    }

    @Test
    public void testSpots() {
        assert boardModel.getSpot('c', 6).getTypeOfSpot() == TypeOfSpot.TRAP;
        assert boardModel.getSpot('f', 6).getTypeOfSpot() == TypeOfSpot.TRAP;
        assert boardModel.getSpot('c', 3).getTypeOfSpot() == TypeOfSpot.TRAP;
        assert boardModel.getSpot('f', 3).getTypeOfSpot() == TypeOfSpot.TRAP;
        assert boardModel.getSpot('a', 8).getTypeOfSpot() == TypeOfSpot.NORMAL;
    }


}
