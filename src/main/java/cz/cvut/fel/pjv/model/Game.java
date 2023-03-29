package cz.cvut.fel.pjv.model;

import cz.cvut.fel.pjv.gui.BoardPanel;
import cz.cvut.fel.pjv.gui.GameFrame;
import cz.cvut.fel.pjv.pieces.Piece;


public class Game {

    public static Piece currentPiece;
    public static String currentPlayer;
    private GameStatus gameStatus;

    private MoveLogger moveLogger;
    private BoardPanel boardPanel;
    private BoardModel boardModel;  // originator
    private GameFrame gameFrame;

    /**
     * Constructor for Game
     */
    public Game() {
        this.gameStatus = GameStatus.ACTIVE;
        currentPiece = null;
        currentPlayer = "gold";
        initGUI();
        initModel();


    }

    private void initModel() {
        boardModel = new BoardModel();
        moveLogger = new MoveLogger();
    }

    private void initGUI() {
        boardPanel = new BoardPanel(this);
        gameFrame = new GameFrame(this);

    }





    /**
     * Check Move for validity.
     *
     * @param sourceX char coordinate of Piece
     * @param sourceY int coordinate of Piece
     * @param destinationX char coordinate of Piece
     * @param destinationY int coordinate of Piece
     */
    public void moveRequest(char sourceX, int sourceY, char destinationX, int destinationY) {
        Move move = new Move(null, sourceX, sourceY, destinationX, destinationY);
        // if move valid
        moveLogger.saveMove(move);
        execute(move);
    }


    private void execute(Move move) {
        // save Move
        boardModel.doMove(move);    // Update move in board model
        boardPanel.makeMove(move);  // Update move in board panel

    }

    public void saveToFile() {
        // TODO save current game set to file
        // location of pieces
    }


    public void undoMove() {
        Move lastMove = moveLogger.undoMove();
        if (lastMove != null) {
            // if valid move
            boardPanel.makeUndo(lastMove);
            boardModel.makeUndo(lastMove);
        }
    }





    // ----- Getters -----


    public BoardPanel getBoardPanel() {
        return boardPanel;
    }

    public BoardModel getBoardModel() {
        return boardModel;
    }

    public MoveLogger getMoveLogger() {
        return moveLogger;
    }

    public static void main(String[] argv) {
        Game game = new Game();
    }

}
