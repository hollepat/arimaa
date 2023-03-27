package cz.cvut.fel.pjv.gui;

import cz.cvut.fel.pjv.model.DragAndDropListener;
import cz.cvut.fel.pjv.model.Game;
import cz.cvut.fel.pjv.pieces.ColorPiece;
import cz.cvut.fel.pjv.pieces.Piece;
import cz.cvut.fel.pjv.pieces.Pieces;
import cz.cvut.fel.pjv.pieces.Type;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.Iterator;

public class BoardPanel extends JPanel {

    private Game game;
    private JLayeredPane boardLayeredPane;      // when we want to drag a Piece --> put Piece into boardLayerdPane --> move --> and put into boardPane again
    private JPanel boardPane;                   // panel that holds all static object
    public JPanel[][] arimaaBoardArray = new JPanel[8][8];
    private final String[] COLS = new String[] {"a","b","c","d","e","f","g","h"};
    private final int SQUARE_DIMENSION = 50;
    private final int PANEL_DIMENSION = 600;


    public BoardPanel(Game game) {
        super(new BorderLayout());
        this.game = game;
        initBoardPanel();
        drawBoard();
        createPieces();
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

    /**
     * Create squares (JPanel) for BoardPanel and coordinates marks.
     */
    private void drawBoard() {

        for (int i = 0; i < arimaaBoardArray.length; i++) {
            for (int j = 0; j < arimaaBoardArray[i].length; j++) {
                JPanel square = new JPanel(new GridLayout(1, 1));       // Square Panels
                square.setPreferredSize(new Dimension(SQUARE_DIMENSION, SQUARE_DIMENSION));
                square.setBorder(BorderFactory.createLineBorder(Color.GRAY));
                if ((i == 2 || i == 5) && (j == 2 || j == 5)) {
                    square.setBackground(Color.BLACK);
                } else {
                    square.setBackground(Color.LIGHT_GRAY);
                }
                arimaaBoardArray[i][j] = square;
            }
        }

        fillAlphabetMarks(0);
        fillNumberMarksAndSquares();
        fillAlphabetMarks(9);

    }

    private void fillNumberMarksAndSquares() {
        for (int i = 1; i < 9; i++) {
            for (int j = 0; j < 10; j++) {
                switch (j) {
                    case 0:     // first column
                    case 9:     // last column
                        boardPane.add(new JLabel("" + (i), SwingConstants.CENTER), i, j);
                        break;
                    default:
                        boardPane.add(arimaaBoardArray[i-1][j-1], i, j);
                }
            }
        }
    }

    private void fillAlphabetMarks(int row) {
        // fill the first col in row chess board
        boardPane.add(new JLabel(""), row, 0);
        // fill the row with "a b c d e f g h"
        for (int i = 0; i < 8; i++) {
            boardPane.add(new JLabel(COLS[i], SwingConstants.CENTER), row, i+1);
        }
        // fill the last col in row chess board
        boardPane.add(new JLabel(""), row, 9);
    }

    private JPanel getSquarePanel(char x, int y) {
        if (x < 'a' || x > 'h' || y < 1 || y > 8) {
            return null;
        } else {
            return arimaaBoardArray[x - 'a'][y-1];
        }
    }

    /**
     * Draw initial set of Pieces on board
     */
    private void createPieces() {

        // rabbit
        Iterator<Piece> silverRabbitsIterator = Pieces.getPieces(ColorPiece.SILVER, Type.RABBIT).iterator();
        Iterator<Piece> goldRabbitsIterator = Pieces.getPieces(ColorPiece.GOLD, Type.RABBIT).iterator();
        for (int col = 0; col < 8; col++) {
            arimaaBoardArray[7][col].add(getImgAsJLabel(silverRabbitsIterator.next()), 0, 0);   // add gold Piece
            arimaaBoardArray[0][col].add(getImgAsJLabel(goldRabbitsIterator.next()), 0, 0);   // add silver Piece

        }

        // cat
        Iterator<Piece> silverCatIterator = Pieces.getPieces(ColorPiece.SILVER, Type.CAT).iterator();
        Iterator<Piece> goldCatIterator = Pieces.getPieces(ColorPiece.GOLD, Type.CAT).iterator();
        arimaaBoardArray[6][0].add(getImgAsJLabel(silverCatIterator.next()));
        arimaaBoardArray[6][7].add(getImgAsJLabel(silverCatIterator.next()));
        arimaaBoardArray[1][0].add(getImgAsJLabel(goldCatIterator.next()));
        arimaaBoardArray[1][7].add(getImgAsJLabel(goldCatIterator.next()));

        // dog
        Iterator<Piece> silverDogIterator = Pieces.getPieces(ColorPiece.SILVER, Type.DOG).iterator();
        Iterator<Piece> goldDogIterator = Pieces.getPieces(ColorPiece.GOLD, Type.DOG).iterator();
        arimaaBoardArray[6][1].add(getImgAsJLabel(silverDogIterator.next()));
        arimaaBoardArray[6][6].add(getImgAsJLabel(silverDogIterator.next()));
        arimaaBoardArray[1][1].add(getImgAsJLabel(goldDogIterator.next()));
        arimaaBoardArray[1][6].add(getImgAsJLabel(goldDogIterator.next()));

        // horse
        Iterator<Piece> silverHorseIterator = Pieces.getPieces(ColorPiece.SILVER, Type.HORSE).iterator();
        Iterator<Piece> goldHorseIterator = Pieces.getPieces(ColorPiece.GOLD, Type.HORSE).iterator();
        arimaaBoardArray[6][2].add(getImgAsJLabel(silverHorseIterator.next()));
        arimaaBoardArray[6][5].add(getImgAsJLabel(silverHorseIterator.next()));
        arimaaBoardArray[1][2].add(getImgAsJLabel(goldHorseIterator.next()));
        arimaaBoardArray[1][5].add(getImgAsJLabel(goldHorseIterator.next()));

        // camel
        Iterator<Piece> silverCamelIterator = Pieces.getPieces(ColorPiece.SILVER, Type.CAMEL).iterator();
        Iterator<Piece> goldCamelIterator = Pieces.getPieces(ColorPiece.GOLD, Type.CAMEL).iterator();
        arimaaBoardArray[6][3].add(getImgAsJLabel(silverCamelIterator.next()));
        arimaaBoardArray[1][4].add(getImgAsJLabel(goldCamelIterator.next()));

        // elephant
        Iterator<Piece> silverElephantIterator = Pieces.getPieces(ColorPiece.SILVER, Type.ELEPHANT).iterator();
        Iterator<Piece> goldElephantIterator = Pieces.getPieces(ColorPiece.GOLD, Type.ELEPHANT).iterator();
        arimaaBoardArray[6][4].add(getImgAsJLabel(silverElephantIterator.next()));
        arimaaBoardArray[1][3].add(getImgAsJLabel(goldElephantIterator.next()));



    }

    /**
     * Give JLabel as img of piece. JLabel represent actual Piece
     * @param piece is instance of Piece, e.g. Rabbit, Camel...
     * @return JLabel containing ImageIcon() of piece
     */
    private JLabel getImgAsJLabel(Piece piece) {
        Image pieceImage = new ImageIcon(getClass().getResource(piece.getImgPath())).getImage();
        System.out.println(getClass().getResource(piece.getImgPath()));
        pieceImage = pieceImage.getScaledInstance(SQUARE_DIMENSION, SQUARE_DIMENSION, Image.SCALE_SMOOTH);
        JLabel pieceImageLabel = new JLabel(new ImageIcon(pieceImage));
        return pieceImageLabel;
    }


    // --------- Mechanism for drag and drop movement ---------
    // TODO write mechanism that will drag and drop them in grid layout

    /**
     * Take Piece from source and put it into JLayeredPane.
     *
     * @param sourceX char coordinate of Piece
     * @param sourceY int coordinate of Piece
     * @param dragX char coordinate of Piece in drag phase
     * @param dragY int coordinate of Piece in drag phase
     */
    public void preDrag(char sourceX, int sourceY, int dragX, int dragY) {
        System.out.println("pre-Drag");
        Piece originPiece = game.getBoardModel().getSpot(sourceX, sourceY).getPiece();
        if (originPiece != null) {
            getSquarePanel(sourceX, sourceY).getComponent(0).setVisible(false); // Piece disappear form boardPane
            JLabel draggedPieceImageLabel = getImgAsJLabel(originPiece);    // Create drag Piece in boardLayeredPane
            draggedPieceImageLabel.setLocation(dragX, dragY);
            draggedPieceImageLabel.setSize(SQUARE_DIMENSION, SQUARE_DIMENSION);
            boardLayeredPane.add(draggedPieceImageLabel, JLayeredPane.DRAG_LAYER);  // drag Piece appear in boardLayeredPane
        }
    }

    /**
     * Move pieceImageLabel (Piece) over window.
     */
    public void drag() {
        // TODO implement
    }

    /**
     * Put pieceImageLabel (Piece) back to boardPane from boardLayeredPane.
     */
    public void postDrag() {
        // TODO implement
    }



}
