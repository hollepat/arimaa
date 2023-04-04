package cz.cvut.fel.pjv.pieces;

import cz.cvut.fel.pjv.model.Game;
import cz.cvut.fel.pjv.model.Move;

import javax.swing.*;
import java.util.logging.Level;

import static cz.cvut.fel.pjv.pieces.ColorPiece.GOLD;
import static cz.cvut.fel.pjv.pieces.ColorPiece.SILVER;


abstract public class Piece extends JLabel {

    protected char positionX;     // a, b, c, d, e, f, g, h
    protected int positionY;      // 1, 2, 3, 4, 5, 6, 7, 8
    protected boolean alive;       // live = true, killed = false
    protected ColorPiece color;
    protected PieceType pieceType;


    /**
     * Constructor for Piece
     * @param positionX the x coordinates of Piece
     * @param positionY the y coordinates of Piece
     * @param alive the state whether if alive or not
     * @param color color of the piece
     */
    public Piece(char positionX, int positionY, boolean alive, ColorPiece color) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.alive = alive;
        this.color = color;
    }

    /**
     * Constructor for Piece.
     * @param color color of the piece
     * @param pieceType sets one these types PieceType
     */
    public Piece(ColorPiece color, PieceType pieceType) {
        this.color = color;
        this.pieceType = pieceType;
    }

    public boolean isValidMove(Move move) {
        System.out.println(move.getPiece().getType());

        if (move.getPiece().getType().equals(PieceType.RABBIT)) {
            return isValidMoveForRabbit(move);
        }
        return true;
    }

    private boolean isValidMoveForRabbit(Move move) {
        ColorPiece color = move.getPiece().getColor();
        return switch (color) {
            case GOLD -> move.getSy() <= move.getDy();
            case SILVER -> move.getSy() >= move.getDy();
        };
    }

    public PieceType getType() {
        return pieceType;
    }

    public ColorPiece getColor() {
        return color;
    }

    public abstract String getImgPath();





}
