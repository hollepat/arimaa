package cz.cvut.fel.pjv.model;

import cz.cvut.fel.pjv.gui.App;
import cz.cvut.fel.pjv.gui.BoardPanel;
import cz.cvut.fel.pjv.gui.GameFrame;
import cz.cvut.fel.pjv.pieces.Piece;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Stack;

// https://stackoverflow.com/questions/21077322/create-a-chess-board-with-jpanel
// how to move Pieces https://www.youtube.com/watch?v=LivX1XKpSQA

public class Game {     // Caretaker for Originator and Memento list

    public static Piece currentPiece;
    public static String currentPlayer;
    private GameStatus gameStatus;
    private ArimaaBoard arimaaBoard;        // is Originator and holds the current state
    private Stack<Memento> history;
    private BoardPanel boardPanel;
    private GameFrame gameFrame;

    /**
     * Constructor for Game
     */
    public Game() {
        this.gameStatus = GameStatus.ACTIVE;
        currentPiece = null;
        currentPlayer = "gold";
        initializeGUI();
    }

    private void initializeGUI() {
        boardPanel = new BoardPanel();
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
        Memento m = arimaaBoard.save();
        history.push(m);
    }

    /**
     * Go back in history of snapshots
     */
    public void undo() {
        Memento m = history.pop();
        arimaaBoard.restore(m);
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


    // ------ getters -----


    public BoardPanel getBoardPanel() {
        return boardPanel;
    }

    public static void main(String[] argv) {
        Game game = new Game();
    }

}
