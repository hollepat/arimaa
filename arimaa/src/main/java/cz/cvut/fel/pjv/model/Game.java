package cz.cvut.fel.pjv.model;

import cz.cvut.fel.pjv.gui.App;
import cz.cvut.fel.pjv.pieces.Piece;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Stack;

// https://stackoverflow.com/questions/21077322/create-a-chess-board-with-jpanel
// how to move Pieces https://www.youtube.com/watch?v=LivX1XKpSQA

public class Game {     // Caretaker for Originator and Memento list

    public static Piece currentPiece;
    public static String currentPlayer;
    private GameStatus gameStatus;
    private ArimaaBoard arimaaBoard;        // is Originator and holds the current state
    private Stack<Memento> history;

    /**
     * Constructor for Game
     */
    public Game() {
        this.gameStatus = GameStatus.ACTIVE;
        currentPiece = null;
        currentPlayer = "gold";

        this.arimaaBoard = new ArimaaBoard();    // create board place and Pieces on the board
    }

    /**
     * Starting method for the Game. Creates Game window
     */
    public void startGame() {
        // TODO draw window and it's components (board with Pieces, save, undo, resign and new button)
        JFrame gameFrame = new JFrame("Arimaa Game");
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setMinimumSize(new Dimension(720, 720));

        // add Components

        JPanel panel = new JPanel(new BorderLayout(3, 3));
        panel.setBorder(new EmptyBorder(5, 5, 5, 5));
        JToolBar tools = new JToolBar();

        tools.setFloatable(false);
        panel.add(tools, BorderLayout.PAGE_START);
        tools.add(new JButton("New")); // TODO - create new game
        tools.add(new JButton("Save")); // TODO - save game
        tools.add(new JButton("Undo")); // TODO - go step back
        tools.add(new JButton("Resign")); // TODO - give up game
        tools.addSeparator();
        tools.add(new JLabel("It works!!"));

        arimaaBoard.drawBoard(panel);

        gameFrame.add(panel);
        App.setFrameCenter(gameFrame);
        gameFrame.pack();
        gameFrame.setVisible(true);

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


}
