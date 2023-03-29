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
        execute(move);
    }


    private void execute(Move move) {
        // save Move
        boardModel.doMove(move);    // Update move in board model
        boardPanel.makeMove(move);  // Update move in board panel

    }

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
