package cz.cvut.fel.pjv.model;


import cz.cvut.fel.pjv.pieces.Piece;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.List;

public class ArimaaBoard {      // might extend JPanel
    // is Originator that holds it's current state
    // TODO draw state of board to GUI

    private State currentState;
    private PlayerGold gold;
    private PlayerSilver silver;
    private JPanel boardPanel;
    private List<Piece> pieces;

    private JPanel[][] chessBoardSquares = new JPanel[8][8];  // test
    private Spot[][] arimaaBoardArray = new Spot[8][8];
    private final String[] COLS = new String[] {"a","b","c","d","e","f","g","h"};




    /**
     * Constructor for Board.
     */
    public ArimaaBoard() {
        this.currentState = new State();
        this.gold = new PlayerGold();
        this.silver = new PlayerSilver();
    }

    /**
     * Save current State in Memento.
     *
     * @return Memento containing the State
     */
    public Memento save() {
        Memento memento = new Memento(currentState);
        return memento;
    }

    /**
     * Restore previous State from Memento at top of the Stack.
     *
     * @param memento Memento containing the State chessBoard will change to
     */
    public void restore(Memento memento) {
        this.currentState = memento.getState();
    }

    /**
     * Create Pieces.
     */
    private void createPieces() {

    }

    /**
     * Draw Board and Pieces on that arimaaBoard.
     *
     * @return
     */
    public void drawBoard(JPanel panel) {

        boardPanel = new JPanel(new GridLayout(0, 10));
        boardPanel.setBorder(new LineBorder(Color.BLACK));
        panel.add(boardPanel);

        // create the chess board squares
        Insets buttonMargin = new Insets(0,0,0,0);
        for (int i = 0; i < chessBoardSquares.length; i++) {
            for (int j = 0; j < chessBoardSquares[i].length; j++) {
                JPanel button = new JPanel();
                Spot spot = new Spot(i, j, TypeOfSpot.NORMAL);
                button.setBorder(BorderFactory.createLineBorder(Color.GRAY));
                //spot.setMargin(buttonMargin);
                //button.setMargin(buttonMargin);
                // our chess pieces are 64x64 px in size, so we'll
                // 'fill this in' using a transparent icon...
                // ImageIcon icon = new ImageIcon(new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB));
                // b.setIcon(icon);
                if ( (i == 2 || i == 5) && (j == 2 || j == 5)) {
                    button.setBackground(Color.BLACK);
                    spot.setBackground(Color.BLACK);
                } else {
                    spot.setBackground(Color.LIGHT_GRAY);
                    button.setBackground(Color.LIGHT_GRAY);
                }
                chessBoardSquares[j][i] = button;
                arimaaBoardArray[j][i] = spot;
            }
        }

        fillAlphabetMarks();
        fillNumberMarks();
        fillAlphabetMarks();
    }

    private void fillNumberMarks() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 10; j++) {
                switch (j) {
                    case 0:     // last column
                    case 9:     // first column
                        boardPanel.add(new JLabel("" + (i + 1), SwingConstants.CENTER));
                        break;
                    default:
                        boardPanel.add(chessBoardSquares[j-1][i]);       // TODO change to arimaaBoardArray
                }
            }
        }
    }

    private void fillAlphabetMarks() {
        // fill the first col in row chess board
        boardPanel.add(new JLabel(""));
        // fill the row with "a b c d e f g h"
        for (int i = 0; i < 8; i++) {
            boardPanel.add(new JLabel(COLS[i], SwingConstants.CENTER));
        }
        // fill the last col in row chess board
        boardPanel.add(new JLabel(""));
    }




}
