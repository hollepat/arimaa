package cz.cvut.fel.pjv.pieces;

import cz.cvut.fel.pjv.model.Move;

import javax.swing.*;



abstract public class Piece extends JLabel {

    protected boolean alive;       // live = true, killed = false
    protected ColorPiece color;
    protected PieceType pieceType;
    protected int pieceStrength;


    /**
     * Constructor for Piece.
     * @param color color of the piece
     * @param pieceType sets one these types PieceType
     */
    public Piece(ColorPiece color, PieceType pieceType) {
        this.color = color;
        this.pieceType = pieceType;
    }

    /**
     * Checks if Piece can move like this.
     *
     * @param move to be validated
     * @return true if move is valid by Piece
     */
    public boolean isValidMove(Move move) {
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
    public int getPieceStrength() {
        return pieceStrength;
    }
}
