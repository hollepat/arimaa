package cz.cvut.fel.pjv.pieces;

import cz.cvut.fel.pjv.model.Move;

import javax.swing.*;



abstract public class Piece extends JLabel {

    protected ColorPiece color;
    protected PieceType pieceType;
    protected int pieceStrength;
    protected boolean alive;


    /**
     * Constructor for Piece.
     * @param color color of the piece
     * @param pieceType sets one these types PieceType
     */
    public Piece(ColorPiece color, PieceType pieceType) {
        this.color = color;
        this.pieceType = pieceType;
        this.alive = true;
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
    public boolean isAlive() {
        return alive;
    }
    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    @Override
    public String toString() {
        return pieceType +
                ", " + color;
    }
}
