package cz.cvut.fel.pjv.model;

import cz.cvut.fel.pjv.pieces.Piece;

import java.util.Stack;

public class Game {     // Caretaker for Originator and Memento list

    public static Piece currentPiece;
    public static String currentPlayer;
    private GameStatus gameStatus;

    private ArimaaBoard chessBoard;        // is Originator and holds the current state
    private Stack<Memento> history;

    /**
     * Constructor for Game
     */
    public Game() {
        this.gameStatus = GameStatus.ACTIVE;
        currentPiece = null;
        currentPlayer = "gold";

        this.chessBoard = new ArimaaBoard();    // create board place and Pieces on the board
    }


    public void startGame() {
        // TODO draw window and it's components (board with Pieces, save button, go back and forward button)
    }

    /**
     * Control logic of game regarding choosing Pieces and handling action with them
     */
    private void eventHandler() {

    }


    /**
     * Create new snapshot of game, after change of game.
     */
    public void makeMove() {
        Memento m = chessBoard.save();
        history.push(m);
    }

    /**
     * Go back in history of snapshots
     */
    public void undo() {
        Memento m = history.pop();
        chessBoard.restore(m);
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


}
