package cz.cvut.fel.pjv.view;

import cz.cvut.fel.pjv.controller.Game;
import cz.cvut.fel.pjv.model.BoardModel;
import cz.cvut.fel.pjv.model.Move;
import cz.cvut.fel.pjv.pieces.ColorPiece;
import cz.cvut.fel.pjv.pieces.Piece;
import cz.cvut.fel.pjv.pieces.PieceType;
import cz.cvut.fel.pjv.pieces.PieceSet;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;

public class BoardPanel extends JPanel {

    private final Game game;
    private final BoardModel boardModel;
    private JLayeredPane boardLayeredPane;      // when we want to drag a Piece --> put Piece into boardLayerdPane --> move --> and put into boardPane again
    private JPanel boardPane;                   // panel that holds all static object

    private JPanel[][] arimaaBoardPanels = new JPanel[8][8];
    private final String[] COLS = new String[] {"a","b","c","d","e","f","g","h"};
    public final int SQUARE_DIMENSION = 60;
    private final int PANEL_DIMENSION = 600;


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

    private void changePositionOfPieces() {
        // TODO allow move Pieces over board as needed
        // TODO on JButton check valid Layout and start normal game
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

    public void removePiece(char x, int y) {
        JPanel square = getSquarePanel(y, x);
        //deadPieces.add((JPanel) square.getComponent(0));
        square.remove(0);
        square.repaint();
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
            Game.logger.log(Level.FINE, "Piece is dragged!");
            JLabel draggedPieceImageLabel = (JLabel) boardLayeredPane.getComponentsInLayer(JLayeredPane.DRAG_LAYER)[0];
            if (draggedPieceImageLabel != null) {
                draggedPieceImageLabel.setLocation(dragX, dragY);
            }
        } catch (Exception e) {
            Game.logger.log(Level.FINE, "No Piece to drag!");
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
        // todo remake so it reads from boardModel position of Pieces
        char offset = 'a';
        for (int i = 0; i < boardModel.BOARD_DIMENSION; i++) {
            for (int j = 0; j < boardModel.BOARD_DIMENSION; j++) {
                char x = (char) ('a' + i);
                System.out.println(x);
                if (boardModel.getSpot(x, j+1).getPiece() != null) {
                    arimaaBoardPanels[j][i].add(getImgAsJLabel(boardModel.getSpot(x, j+1).getPiece()));
                }
            }
        }
//
//        // rabbit
//        Iterator<Piece> silverRabbitsIterator = PieceSet.getPieces(ColorPiece.SILVER, PieceType.RABBIT).iterator();
//        Iterator<Piece> goldRabbitsIterator = PieceSet.getPieces(ColorPiece.GOLD, PieceType.RABBIT).iterator();
//        for (int col = 0; col < 8; col++) {
//            arimaaBoardPanels[7][col].add(getImgAsJLabel(silverRabbitsIterator.next()), 0, 0);   // add gold Piece
//            arimaaBoardPanels[0][col].add(getImgAsJLabel(goldRabbitsIterator.next()), 0, 0);   // add silver Piece
//        }
//
//        // cat
//        Iterator<Piece> silverCatIterator = PieceSet.getPieces(ColorPiece.SILVER, PieceType.CAT).iterator();
//        Iterator<Piece> goldCatIterator = PieceSet.getPieces(ColorPiece.GOLD, PieceType.CAT).iterator();
//        arimaaBoardPanels[6][0].add(getImgAsJLabel(silverCatIterator.next()));
//        arimaaBoardPanels[6][7].add(getImgAsJLabel(silverCatIterator.next()));
//        arimaaBoardPanels[1][0].add(getImgAsJLabel(goldCatIterator.next()));
//        arimaaBoardPanels[1][7].add(getImgAsJLabel(goldCatIterator.next()));
//
//        // dog
//        Iterator<Piece> silverDogIterator = PieceSet.getPieces(ColorPiece.SILVER, PieceType.DOG).iterator();
//        Iterator<Piece> goldDogIterator = PieceSet.getPieces(ColorPiece.GOLD, PieceType.DOG).iterator();
//        arimaaBoardPanels[6][1].add(getImgAsJLabel(silverDogIterator.next()));
//        arimaaBoardPanels[6][6].add(getImgAsJLabel(silverDogIterator.next()));
//        arimaaBoardPanels[1][1].add(getImgAsJLabel(goldDogIterator.next()));
//        arimaaBoardPanels[1][6].add(getImgAsJLabel(goldDogIterator.next()));
//
//        // horse
//        Iterator<Piece> silverHorseIterator = PieceSet.getPieces(ColorPiece.SILVER, PieceType.HORSE).iterator();
//        Iterator<Piece> goldHorseIterator = PieceSet.getPieces(ColorPiece.GOLD, PieceType.HORSE).iterator();
//        arimaaBoardPanels[6][2].add(getImgAsJLabel(silverHorseIterator.next()));
//        arimaaBoardPanels[6][5].add(getImgAsJLabel(silverHorseIterator.next()));
//        arimaaBoardPanels[1][2].add(getImgAsJLabel(goldHorseIterator.next()));
//        arimaaBoardPanels[1][5].add(getImgAsJLabel(goldHorseIterator.next()));
//
//        // camel
//        Iterator<Piece> silverCamelIterator = PieceSet.getPieces(ColorPiece.SILVER, PieceType.CAMEL).iterator();
//        Iterator<Piece> goldCamelIterator = PieceSet.getPieces(ColorPiece.GOLD, PieceType.CAMEL).iterator();
//        arimaaBoardPanels[6][3].add(getImgAsJLabel(silverCamelIterator.next()));
//        arimaaBoardPanels[1][4].add(getImgAsJLabel(goldCamelIterator.next()));
//
//        // elephant
//        Iterator<Piece> silverElephantIterator = PieceSet.getPieces(ColorPiece.SILVER, PieceType.ELEPHANT).iterator();
//        Iterator<Piece> goldElephantIterator = PieceSet.getPieces(ColorPiece.GOLD, PieceType.ELEPHANT).iterator();
//        arimaaBoardPanels[6][4].add(getImgAsJLabel(silverElephantIterator.next()));
//        arimaaBoardPanels[1][3].add(getImgAsJLabel(goldElephantIterator.next()));

    }


}
