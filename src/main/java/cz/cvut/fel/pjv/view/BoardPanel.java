package cz.cvut.fel.pjv.view;

import cz.cvut.fel.pjv.controller.Game;
import cz.cvut.fel.pjv.controller.GameStatus;
import cz.cvut.fel.pjv.model.BoardModel;
import cz.cvut.fel.pjv.model.Move;
import cz.cvut.fel.pjv.pieces.Piece;
import cz.cvut.fel.pjv.utils.CharIntTuple;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;

public class BoardPanel extends JPanel {

    private final Game game;
    private final BoardModel boardModel;
    private JLayeredPane boardLayeredPane;      // when we want to drag a Piece --> put Piece into boardLayerdPane --> move --> and put into boardPane again
    private JPanel boardPane;                   // panel that holds all static object

    private final JPanel[][] arimaaBoardPanels = new JPanel[8][8];
    private final String[] COLS = new String[] {"a","b","c","d","e","f","g","h"};
    public final int SQUARE_DIMENSION = 60;
    private final int PANEL_DIMENSION = 600;
    private final int BOARD_DIMENSION = 8;

    // switch Pieces
    private CharIntTuple switch1;
    private CharIntTuple switch2;

    /**
     * Constructor for BoardPanel.
     * @param game is reference to Game object
     */
    public BoardPanel(Game game) {
        super(new BorderLayout());
        this.game = game;
        this.boardModel = game.getBoardModel();
        initBoardPanel();
        drawBoard();
        createPieces();
        boardPane.repaint();
    }

    private void initBoardPanel() {
        boardPane = new JPanel(new GridLayout(10, 10));
        boardPane.setBounds(0, 0, PANEL_DIMENSION, PANEL_DIMENSION);
        boardPane.setBorder(new LineBorder(Color.BLACK));

        // layer for drag action
        boardLayeredPane = new JLayeredPane();
        boardLayeredPane.setPreferredSize(new Dimension(PANEL_DIMENSION, PANEL_DIMENSION));
        boardLayeredPane.add(boardPane, JLayeredPane.DEFAULT_LAYER);
        boardLayeredPane.setVisible(true);

        // add Listeners
        DragAndDropListener pieceDragAndDropListener = new DragAndDropListener(this);
        boardLayeredPane.addMouseListener(pieceDragAndDropListener);
        boardLayeredPane.addMouseMotionListener(pieceDragAndDropListener);
        this.add(boardLayeredPane, BorderLayout.CENTER);
    }


    private JPanel getSquarePanel(int y, char x) {
        if (x < 'a' || x > 'h' || y < 1 || y > 8) {
            return null;
        } else {
            return arimaaBoardPanels[y-1][x-'a'];
        }
    }

    /**
     * Give JLabel as img of piece. JLabel represent actual Piece
     * @param piece is instance of Piece, e.g. Rabbit, Camel...
     * @return JLabel containing ImageIcon() of piece
     */
    private JLabel getImgAsJLabel(Piece piece) {
        Image pieceImage = new ImageIcon(Objects.requireNonNull(getClass().getResource(piece.getImgPath()))).getImage();
        pieceImage = pieceImage.getScaledInstance(SQUARE_DIMENSION-10, SQUARE_DIMENSION-10, Image.SCALE_SMOOTH);
        return new JLabel(new ImageIcon(pieceImage));
    }

    /**
     * Submit Move to Game, regarding validity and execution.
     *
     * @param sourceX char coordinate of Piece
     * @param sourceY int coordinate of Piece
     * @param destinationX char coordinate of Piece
     * @param destinationY int coordinate of Piece
     */
    public void submitMoveRequest(char sourceX, int sourceY, char destinationX, int destinationY) {
        Game.logger.log(Level.INFO, "Submit move request!");
        try {
            if (getSquarePanel(sourceY, sourceX) != null) {
                getSquarePanel(sourceY, sourceX).getComponent(0).setVisible(true);
            }
            game.moveRequest(sourceX, sourceY, destinationX, destinationY);
            //game.move(sourceX, sourceY, destinationX, destinationY);
        } catch (ArrayIndexOutOfBoundsException e) {
            Game.logger.log(Level.WARNING, "Move request was not submitted due error!");
        }
    }

    /**
     * Move Piece from source to destination and sets it to visible. If move effects other Pieces do as well.
     *
     * @param move validated Move
     */
    public void makeMove(Move move) {
        JPanel sourceSquarePanel = getSquarePanel(move.getSy(), move.getSx());
        JPanel destinationSquarePanel = getSquarePanel(move.getDy(), move.getDx());

        destinationSquarePanel.removeAll();
        destinationSquarePanel.add(sourceSquarePanel.getComponent(0));
        destinationSquarePanel.repaint();
        sourceSquarePanel.removeAll();
        sourceSquarePanel.repaint();
    }

    /**
     * Undo move in GUI.
     *
     * @param move from moveHistory
     */
    public void makeUndo(Move move) {
        JPanel sourceSquarePanel = getSquarePanel(move.getSy(), move.getSx());
        JPanel destinationSquarePanel = getSquarePanel(move.getDy(), move.getDx());

        sourceSquarePanel.add(getImgAsJLabel(move.getPiece()));
        destinationSquarePanel.removeAll();
        destinationSquarePanel.repaint();
        sourceSquarePanel.repaint();

        // bring back killed pieces
        for (Map.Entry<String, Piece> entry : move.getKilledPieces().entrySet()) {
            if (entry.getValue() != move.getPiece()) {
                JPanel square = getSquarePanel(Integer.parseInt(String.valueOf(entry.getKey().charAt(1))), entry.getKey().charAt(0));
                square.add(getImgAsJLabel(entry.getValue()));
                square.repaint();
            }
        }

        SwingUtilities.updateComponentTreeUI(boardPane);    // problem - square got repainted in next move
    }

    /**
     * Remove Piece from Spot x y.
     *
     * @param x coordinate of Piece to be removed
     * @param y coordinate of Piece to be removed
     */
    public void removePiece(char x, int y) {
        JPanel square = getSquarePanel(y, x);
        square.remove(0);
        square.repaint();
    }

    /**
     * Switch two Pieces on the board.
     *
     * @param x1 coordinate of piece 1
     * @param y1 coordinate of piece 1
     * @param x2 coordinate of piece 2
     * @param y2 coordinate of piece 2
     */
    public void makeSwitch(char x1, int y1, char x2, int y2) {
        // get panels where Pieces are
        JPanel panel1 = getSquarePanel(y1, x1);
        JPanel panel2 = getSquarePanel(y2, x2);

        // tmp store Piece in panel2
        Component tmp = panel2.getComponent(0);

        // remove any Piece from panel2
        panel2.removeAll();

        // add switched Piece
        panel2.add(panel1.getComponent(0));

        // refresh panel2
        panel2.repaint();

        // remove any Piece from panel1
        panel1.removeAll();

        // add switched Piece
        panel1.add(tmp);

        // refresh panel1
        panel1.repaint();
    }

    /**
     * Request to switch from GUI input.
     *
     * @param x coordinate of Piece to be switched
     * @param y coordinate of Piece to be switched
     *
     */
    public void submitSwitchRequest(char x, int y) {

        if (game.getGameStatus() != GameStatus.SETUP_LAYOUT) {
            Game.logger.log(Level.WARNING, "To change position of Pieces set Game to SETUP mode!");
            return;
        }

        // no Piece has been chosen yet
        if (switch1 == null) {
            // choose switch 1 and wait for switch 2
            switch1 = new CharIntTuple(x, y);
            Game.logger.log(Level.INFO, "Switch1 chosen: " + x + " " + y);
            game.getGameFrame().changeMsg("Choose another piece to switch place with piece on " + x + " " + y);
            return;
        }

        // choose second Piece to switch --> and switch them
        switch2 = new CharIntTuple(x, y);
        Game.logger.log(Level.INFO, "Switch2 chosen: " + x + " " + y);
        game.switchRequest(switch1.getCharacter(), switch1.getInteger(), switch2.getCharacter(), switch2.getInteger());

        // reset switch1 and switch2
        switch1 = null;
        switch2 = null;
        game.getGameFrame().changeMsg("Pres Play to start a Game!");

    }

    // --------------------------------------------------------
    // --------- Mechanism for drag and drop movement ---------
    // --------------------------------------------------------

    /**
     * Take pieceImageLabel (Piece) from source and put its copy into JLayeredPane. Original pieceImageLabel is
     * still at source --> setVisible(false).
     *
     * @param sourceX char coordinate of Piece
     * @param sourceY int coordinate of Piece
     * @param dragX char coordinate of Piece in drag phase
     * @param dragY int coordinate of Piece in drag phase
     */
    public void preDrag(char sourceX, int sourceY, int dragX, int dragY) {
        Game.logger.log(Level.INFO, "Piece picked!");
        if (game.getBoardModel().getSpot(sourceX, sourceY).getPiece() == null) {
            Game.logger.log(Level.WARNING, "On position x " + dragX + " y " + dragY + " is no Piece!");
            return;
        }
        Piece originPiece = game.getBoardModel().getSpot(sourceX, sourceY).getPiece();
        Game.logger.log(Level.INFO, "Picked Piece: " + originPiece.getType() + "!");
        getSquarePanel(sourceY, sourceX).getComponent(0).setVisible(false); // Piece disappear form boardPane but is still there
        JLabel draggedPieceImageLabel = getImgAsJLabel(originPiece);    // Create drag Piece in boardLayeredPane
        draggedPieceImageLabel.setLocation(dragX, dragY);
        draggedPieceImageLabel.setSize(SQUARE_DIMENSION, SQUARE_DIMENSION);
        boardLayeredPane.add(draggedPieceImageLabel, JLayeredPane.DRAG_LAYER);  // drag Piece appear in boardLayeredPane
    }

    /**
     * Move pieceImageLabel (Piece) over BoardPanel.
     *
     * @param dragX char coordinate of Piece in drag phase
     * @param dragY int coordinate of Piece in drag phase
     */
    public void drag(int dragX, int dragY) {
        try {
            Game.logger.log(Level.FINER, "Piece is dragged!");
            JLabel draggedPieceImageLabel = (JLabel) boardLayeredPane.getComponentsInLayer(JLayeredPane.DRAG_LAYER)[0];
            if (draggedPieceImageLabel != null) {
                draggedPieceImageLabel.setLocation(dragX, dragY);
            }
        } catch (Exception e) {
            Game.logger.log(Level.FINER, "No Piece to drag!");
        }
    }

    /**
     * Remove pieceImageLabel (Piece) from JLayeredPane, when drag ends.
     */
    public void postDrag() {
        try {
            Game.logger.log(Level.INFO, "Piece dropped!");
            JLabel draggedPieceImageLabel = (JLabel) boardLayeredPane.getComponentsInLayer(JLayeredPane.DRAG_LAYER)[0];
            boardLayeredPane.remove(draggedPieceImageLabel);
            boardLayeredPane.repaint();
        } catch (Exception e) {
            Game.logger.log(Level.WARNING, "No Piece were dropped!");
        }
    }

    // --------------------------------------------------------
    // -------------- Create Board and Pieces -----------------
    // --------------------------------------------------------

    /**
     * Create squares (JPanel) for BoardPanel and coordinates marks.
     */
    private void drawBoard() {
        for (int i = 0; i < arimaaBoardPanels.length; i++) {
            for (int j = 0; j < arimaaBoardPanels[i].length; j++) {
                JPanel square = new JPanel(new GridLayout(1, 1));       // Square Panels
                square.setPreferredSize(new Dimension(SQUARE_DIMENSION, SQUARE_DIMENSION));
                square.setSize(new Dimension(SQUARE_DIMENSION, SQUARE_DIMENSION));
                square.setBorder(BorderFactory.createLineBorder(Color.GRAY));
                if ((i == 2 || i == 5) && (j == 2 || j == 5)) {
                    square.setBackground(Color.BLACK);
                } else {
                    square.setBackground(Color.LIGHT_GRAY);
                }
                arimaaBoardPanels[i][j] = square;
            }
        }

        fillAlphabetMarks(0);
        fillNumberMarksAndSquares();
        fillAlphabetMarks(9);

    }

    private JLabel createMarkLabel(String text) {
        JLabel mark = new JLabel(text, SwingConstants.CENTER);
        mark.setPreferredSize(new Dimension(SQUARE_DIMENSION, SQUARE_DIMENSION));
        mark.setSize(new Dimension(SQUARE_DIMENSION, SQUARE_DIMENSION));
        return mark;
    }

    private void fillNumberMarksAndSquares() {

        for (int i = 1; i < 9; i++) {
            for (int j = 0; j < 10; j++) {
                switch (j) {     // first column
                    case 0, 9 ->     // last column
                         boardPane.add(createMarkLabel("" + i), i , j);
                    default -> boardPane.add(arimaaBoardPanels[i - 1][j - 1], i, j);
                }
            }
        }
    }

    private void fillAlphabetMarks(int row) {
        // fill the first col in row chess board
        boardPane.add(createMarkLabel(""), row, 0);
        // fill the row with "a b c d e f g h"
        for (int i = 0; i < 8; i++) {
            boardPane.add(createMarkLabel(COLS[i]), row, i+1);
        }
        // fill the last col in row chess board
        boardPane.add(createMarkLabel(""), row, 9);
    }

    private void createPieces() {
            for (int i = 0; i < BOARD_DIMENSION; i++) {
                for (int j = 0; j < BOARD_DIMENSION; j++) {
                    char x = (char) ('a' + i);
                    if (boardModel.getSpot(x, j+1).getPiece() != null) {
                        arimaaBoardPanels[j][i].add(getImgAsJLabel(boardModel.getSpot(x, j+1).getPiece()));
                    }
                }
            }
    }


}
