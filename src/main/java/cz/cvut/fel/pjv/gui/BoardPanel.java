package cz.cvut.fel.pjv.gui;

import cz.cvut.fel.pjv.model.Game;
import cz.cvut.fel.pjv.model.Spot;
import cz.cvut.fel.pjv.model.TypeOfSpot;
import cz.cvut.fel.pjv.pieces.ColorPiece;
import cz.cvut.fel.pjv.pieces.Piece;
import cz.cvut.fel.pjv.pieces.Pieces;
import cz.cvut.fel.pjv.pieces.Type;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;

public class BoardPanel extends JPanel {

    private Game game;
    private JLayeredPane boardLayeredPane;      // when we want to drag a Piece --> put Piece into boardLayerdPane --> move --> and put into boardPane again
    private JPanel boardPane;                   // panel that holds all static object
    public JPanel[][] arimaaBoardSquares = new JPanel[8][8];
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
        this.add(boardLayeredPane, BorderLayout.CENTER);
    }

    /**
     * Create squares (JPanel) for BoardPanel.
     */
    private void drawBoard() {

        for (int i = 0; i < arimaaBoardSquares.length; i++) {
            for (int j = 0; j < arimaaBoardSquares[i].length; j++) {
                JPanel square = new JPanel(new GridLayout(1, 1));       // Square Panels
                square.setPreferredSize(new Dimension(SQUARE_DIMENSION, SQUARE_DIMENSION));
                square.setBorder(BorderFactory.createLineBorder(Color.GRAY));
                if ((i == 2 || i == 5) && (j == 2 || j == 5)) {
                    square.setBackground(Color.BLACK);
                } else {
                    square.setBackground(Color.LIGHT_GRAY);
                }
                arimaaBoardSquares[i][j] = square;
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
                        boardPane.add(arimaaBoardSquares[i-1][j-1], i, j);
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

    private JPanel getSquarePanel(char i, int j) {
        if (i < 'a' || i > 'h' || j < 1 || j > 8) {
            return null;
        } else {
            return arimaaBoardSquares[i - 'a'][j-1];
        }
    }

    /**
     * Draw initial set of Pieces on board
     */
    private void createPieces() {
        // TODO create instances of Pieces in gui

        // rabbit
        Iterator<Piece> silverRabbitsIterator = Pieces.getPieces(ColorPiece.SILVER, Type.RABBIT).iterator();
        Iterator<Piece> goldRabbitsIterator = Pieces.getPieces(ColorPiece.GOLD, Type.RABBIT).iterator();
        for (int col = 0; col < 8; col++) {
            arimaaBoardSquares[7][col].add(getImgAsJLabel(silverRabbitsIterator.next()), 0, 0);   // add gold Piece
            arimaaBoardSquares[0][col].add(getImgAsJLabel(goldRabbitsIterator.next()), 0, 0);   // add silver Piece

        }

        // cat
        Iterator<Piece> silverCatIterator = Pieces.getPieces(ColorPiece.SILVER, Type.CAT).iterator();
        Iterator<Piece> goldCatIterator = Pieces.getPieces(ColorPiece.GOLD, Type.CAT).iterator();
        arimaaBoardSquares[6][0].add(getImgAsJLabel(silverCatIterator.next()));
        arimaaBoardSquares[6][7].add(getImgAsJLabel(silverCatIterator.next()));
        arimaaBoardSquares[1][0].add(getImgAsJLabel(goldCatIterator.next()));
        arimaaBoardSquares[1][7].add(getImgAsJLabel(goldCatIterator.next()));

        // dog
        Iterator<Piece> silverDogIterator = Pieces.getPieces(ColorPiece.SILVER, Type.DOG).iterator();
        Iterator<Piece> goldDogIterator = Pieces.getPieces(ColorPiece.GOLD, Type.DOG).iterator();
        arimaaBoardSquares[6][1].add(getImgAsJLabel(silverDogIterator.next()));
        arimaaBoardSquares[6][6].add(getImgAsJLabel(silverDogIterator.next()));
        arimaaBoardSquares[1][1].add(getImgAsJLabel(goldDogIterator.next()));
        arimaaBoardSquares[1][6].add(getImgAsJLabel(goldDogIterator.next()));

        // horse
        Iterator<Piece> silverHorseIterator = Pieces.getPieces(ColorPiece.SILVER, Type.HORSE).iterator();
        Iterator<Piece> goldHorseIterator = Pieces.getPieces(ColorPiece.GOLD, Type.HORSE).iterator();
        arimaaBoardSquares[6][2].add(getImgAsJLabel(silverHorseIterator.next()));
        arimaaBoardSquares[6][5].add(getImgAsJLabel(silverHorseIterator.next()));
        arimaaBoardSquares[1][2].add(getImgAsJLabel(goldHorseIterator.next()));
        arimaaBoardSquares[1][5].add(getImgAsJLabel(goldHorseIterator.next()));

        // camel
        Iterator<Piece> silverCamelIterator = Pieces.getPieces(ColorPiece.SILVER, Type.CAMEL).iterator();
        Iterator<Piece> goldCamelIterator = Pieces.getPieces(ColorPiece.GOLD, Type.CAMEL).iterator();
        arimaaBoardSquares[6][3].add(getImgAsJLabel(silverCamelIterator.next()));
        arimaaBoardSquares[1][4].add(getImgAsJLabel(goldCamelIterator.next()));

        // elephant
        Iterator<Piece> silverElephantIterator = Pieces.getPieces(ColorPiece.SILVER, Type.ELEPHANT).iterator();
        Iterator<Piece> goldElephantIterator = Pieces.getPieces(ColorPiece.GOLD, Type.ELEPHANT).iterator();
        arimaaBoardSquares[6][4].add(getImgAsJLabel(silverElephantIterator.next()));
        arimaaBoardSquares[1][3].add(getImgAsJLabel(goldElephantIterator.next()));



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
     * @param sourceX
     * @param sourceY
     * @param dragX
     * @param dragY
     */
    public void preDrag(char sourceX, int sourceY, int dragX, int dragY) {
        System.out.println("pre-Drag");
        // TODO Piece originPiece = BoardModel.getPiece(sourceX, sourceY);
        Piece originPiece = null;
        if (originPiece != null) {
            getSquarePanel(sourceX, sourceY).getComponent(0).setVisible(false);
            JLabel draggedPieceImageLabel = getImgAsJLabel(originPiece);
            draggedPieceImageLabel.setLocation(dragX, dragY);
            draggedPieceImageLabel.setSize(SQUARE_DIMENSION, SQUARE_DIMENSION);
            boardLayeredPane.add(draggedPieceImageLabel, JLayeredPane.DRAG_LAYER);
        }
    }

    /**
     * Move pieceImageLabel over window.
     */
    public void drag() {

    }

    /**
     * Put pieceImageLabel back to boardPane from boardLayeredPane.
     */
    public void postDrag() {

    }



}
