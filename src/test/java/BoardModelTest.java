import cz.cvut.fel.pjv.model.BoardModel;
import cz.cvut.fel.pjv.model.Move;
import cz.cvut.fel.pjv.model.TypeOfSpot;
import cz.cvut.fel.pjv.pieces.Piece;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BoardModelTest {

    BoardModel boardModel;

    @BeforeEach
    public void setUp() {
        boardModel = new BoardModel();
    }

    @Test
    public void testSpotsTypes() {
        assert boardModel.getSpot('c', 6).getTypeOfSpot() == TypeOfSpot.TRAP;
        assert boardModel.getSpot('f', 6).getTypeOfSpot() == TypeOfSpot.TRAP;
        assert boardModel.getSpot('c', 3).getTypeOfSpot() == TypeOfSpot.TRAP;
        assert boardModel.getSpot('f', 3).getTypeOfSpot() == TypeOfSpot.TRAP;
        assert boardModel.getSpot('a', 8).getTypeOfSpot() == TypeOfSpot.NORMAL;
    }

    @Test
    public void movePieceTest() {
        Piece p = boardModel.getSpot('a', 2).getPiece();
        Move move = new Move(p, 'a', 2, 'a', 3, null);
        boardModel.doMove(move);

        assert boardModel.getSpot('a', 2).getPiece() == null;
        assert boardModel.getSpot('a', 3).getPiece() == p;
    }

    @Test
    public void removePieceTest() {
        Piece p = boardModel.getSpot('a', 2).getPiece();
        boardModel.removePiece(p, 'a', 2);

        assert boardModel.getSpot('a', 2).getPiece() == null;
    }

    @Test
    public void undoMoveTest() {
        Piece p = boardModel.getSpot('a', 2).getPiece();
        Move move = new Move(p, 'a', 2, 'a', 3, null);
        boardModel.doMove(move);

        assert boardModel.getSpot('a', 2).getPiece() == null;
        assert boardModel.getSpot('a', 3).getPiece() == p;

        boardModel.undoMove(move);

        assert boardModel.getSpot('a', 2).getPiece() == p;
        assert boardModel.getSpot('a', 3).getPiece() == null;
    }

    @Test
    public void switchPiecesTest() {
        Piece p1 = boardModel.getSpot('c', 1).getPiece();
        Piece p2 = boardModel.getSpot('d', 1).getPiece();

        boardModel.doSwitch('c', 1, 'd', 1);

        assert boardModel.getSpot('c', 1).getPiece() == p2;
        assert boardModel.getSpot('d', 1).getPiece() == p1;

    }


}
