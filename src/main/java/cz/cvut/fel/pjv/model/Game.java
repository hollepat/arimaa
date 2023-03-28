package cz.cvut.fel.pjv.model;

import cz.cvut.fel.pjv.gui.BoardPanel;
import cz.cvut.fel.pjv.gui.GameFrame;
import cz.cvut.fel.pjv.pieces.Piece;

import java.util.Stack;

public class Game {     // Caretaker

    public static Piece currentPiece;
    public static String currentPlayer;
    private GameStatus gameStatus;

    private Stack<Memento> history;
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
    }

    private void initGUI() {
        boardPanel = new BoardPanel(this);
        gameFrame = new GameFrame(this);

    }


    // ----- event logic -------


    /**
     * Create a new snapshot containing Move.
     */
    public void doMove() {
        Memento m = boardModel.saveMove();
        history.push(m);
    }

    /**
     * Return last made Move.
     */
    public void undoMove() {
        Memento m = history.pop();
        boardModel.restoreMove(m);
    }

    /**
     * Save current snapshot of game to file.
     */
    public void saveToFile() {
        // TODO save current game set to file
        // location of pieces
    }






    // ----- Getters -----


    public BoardPanel getBoardPanel() {
        return boardPanel;
    }

    public BoardModel getBoardModel() {
        return boardModel;
    }

    public static void main(String[] argv) {
        Game game = new Game();
    }

}
