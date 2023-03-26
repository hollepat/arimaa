package cz.cvut.fel.pjv.gui;

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

    //private JPanel board;
    public JPanel[][] arimaaBoardSquares = new JPanel[8][8];
    private final String[] COLS = new String[] {"a","b","c","d","e","f","g","h"};


    public BoardPanel() {
        initBoardPanel();
        drawBoard();
        createPieces();
    }

    private void initBoardPanel() {
        //board = new JPanel(new GridLayout(10, 10));
        //board.setBorder(new LineBorder(Color.BLACK));
        setLayout(new GridLayout(10, 10));
        setBorder(new LineBorder(Color.BLACK));
    }

    /**
     * Create squares (JPanel) for BoardPanel.
     */
    private void drawBoard() {

        for (int i = 0; i < arimaaBoardSquares.length; i++) {
            for (int j = 0; j < arimaaBoardSquares[i].length; j++) {
                JPanel square = new JPanel(new GridLayout(1, 1));       // Square Panels
                square.setBorder(BorderFactory.createLineBorder(Color.GRAY));
                if ( (i == 2 || i == 5) && (j == 2 || j == 5)) {
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
                        add(new JLabel("" + (i), SwingConstants.CENTER), i, j);
                        break;
                    default:
                        add(arimaaBoardSquares[i-1][j-1], i, j);       // TODO change to arimaaBoardArray
                }
            }
        }
    }

    private void fillAlphabetMarks(int row) {
        // fill the first col in row chess board
        add(new JLabel(""), row, 0);
        // fill the row with "a b c d e f g h"
        for (int i = 0; i < 8; i++) {
            add(new JLabel(COLS[i], SwingConstants.CENTER), row, i+1);
        }
        // fill the last col in row chess board
        add(new JLabel(""), row, 9);
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
            arimaaBoardSquares[7][col].add(getImgAsJLabel(silverRabbitsIterator.next().getImgPath()), 0, 0);   // add gold Piece
            arimaaBoardSquares[0][col].add(getImgAsJLabel(goldRabbitsIterator.next().getImgPath()), 0, 0);   // add silver Piece

        }

        // cat
        Iterator<Piece> silverCatIterator = Pieces.getPieces(ColorPiece.SILVER, Type.CAT).iterator();
        Iterator<Piece> goldCatIterator = Pieces.getPieces(ColorPiece.GOLD, Type.CAT).iterator();
        arimaaBoardSquares[6][0].add(getImgAsJLabel(silverCatIterator.next().getImgPath()));
        arimaaBoardSquares[6][7].add(getImgAsJLabel(silverCatIterator.next().getImgPath()));
        arimaaBoardSquares[1][0].add(getImgAsJLabel(goldCatIterator.next().getImgPath()));
        arimaaBoardSquares[1][7].add(getImgAsJLabel(goldCatIterator.next().getImgPath()));

        // dog
        Iterator<Piece> silverDogIterator = Pieces.getPieces(ColorPiece.SILVER, Type.DOG).iterator();
        Iterator<Piece> goldDogIterator = Pieces.getPieces(ColorPiece.GOLD, Type.DOG).iterator();
        arimaaBoardSquares[6][1].add(getImgAsJLabel(silverDogIterator.next().getImgPath()));
        arimaaBoardSquares[6][6].add(getImgAsJLabel(silverDogIterator.next().getImgPath()));
        arimaaBoardSquares[1][1].add(getImgAsJLabel(goldDogIterator.next().getImgPath()));
        arimaaBoardSquares[1][6].add(getImgAsJLabel(goldDogIterator.next().getImgPath()));

        // horse
        Iterator<Piece> silverHorseIterator = Pieces.getPieces(ColorPiece.SILVER, Type.HORSE).iterator();
        Iterator<Piece> goldHorseIterator = Pieces.getPieces(ColorPiece.GOLD, Type.HORSE).iterator();
        arimaaBoardSquares[6][2].add(getImgAsJLabel(silverHorseIterator.next().getImgPath()));
        arimaaBoardSquares[6][5].add(getImgAsJLabel(silverHorseIterator.next().getImgPath()));
        arimaaBoardSquares[1][2].add(getImgAsJLabel(goldHorseIterator.next().getImgPath()));
        arimaaBoardSquares[1][5].add(getImgAsJLabel(goldHorseIterator.next().getImgPath()));

        // camel
        Iterator<Piece> silverCamelIterator = Pieces.getPieces(ColorPiece.SILVER, Type.CAMEL).iterator();
        Iterator<Piece> goldCamelIterator = Pieces.getPieces(ColorPiece.GOLD, Type.CAMEL).iterator();
        arimaaBoardSquares[6][3].add(getImgAsJLabel(silverCamelIterator.next().getImgPath()));
        arimaaBoardSquares[1][4].add(getImgAsJLabel(goldCamelIterator.next().getImgPath()));

        // elephant
        Iterator<Piece> silverElephantIterator = Pieces.getPieces(ColorPiece.SILVER, Type.ELEPHANT).iterator();
        Iterator<Piece> goldElephantIterator = Pieces.getPieces(ColorPiece.GOLD, Type.ELEPHANT).iterator();
        arimaaBoardSquares[6][4].add(getImgAsJLabel(silverElephantIterator.next().getImgPath()));
        arimaaBoardSquares[1][3].add(getImgAsJLabel(goldElephantIterator.next().getImgPath()));



    }

    private JLabel getImgAsJLabel(String imgPath) {
        Image pieceImage = new ImageIcon(getClass().getResource(imgPath)).getImage();
        System.out.println(getClass().getResource(imgPath));
        pieceImage = pieceImage.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        JLabel pieceImageLabel = new JLabel(new ImageIcon(pieceImage));
        return pieceImageLabel;
    }

    // TODO write mechanism that will drag and drop them in grid layout



    public JPanel[][] getArimaaBoardSquares() {
        return arimaaBoardSquares;
    }

}
