package cz.cvut.fel.pjv.model;

import cz.cvut.fel.pjv.gui.BoardPanel;
import cz.cvut.fel.pjv.gui.GameFrame;
import cz.cvut.fel.pjv.pieces.Piece;

import java.util.Stack;

public class Game {

    public static Piece currentPiece;
    public static String currentPlayer;
    private GameStatus gameStatus;
    private State currentState;
    private Stack<Memento> history;
    private BoardPanel boardPanel;
    private BoardModel boardModel;
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
     * Control logic of game regarding choosing Pieces and handling action with them
     */
    private void eventHandler() {

    }

    /**
     * Create new snapshot of game, after change of game.
     */
    public void makeMove() {
        //Memento m = arimaaBoard.save();
        //history.push(m);
    }

    /**
     * Go back in history of snapshots
     */
    public void undo() {
        //Memento m = history.pop();
        //arimaaBoard.restore(m);
    }

    /**
     * Save current snapshot of game to file.
     */
    public void saveToFile() {

    }

    public void selectPiece() {

    }

    public void deselectPiece() {

    }

    public void killPiece() {

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
