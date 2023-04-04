package cz.cvut.fel.pjv.pieces;

public class Horse extends Piece{
    private String pathSilver = "/pieces/horse-silver.png";
    private String pathGold = "/pieces/horse-gold.png";

    /**
     * Constructor for Piece
     *
     * @param positionX the x location of Piece
     * @param positionY the y location of Piece
     * @param alive     the state whether if alive or not
     */
    public Horse(char positionX, int positionY, boolean alive, ColorPiece color) {
        super(positionX, positionY, alive, color);
    }

    public Horse(ColorPiece color) {
        super(color, PieceType.HORSE);
    }

    @Override
    public String getImgPath() {
        if (color == ColorPiece.GOLD) {
            return pathGold;
        } else {
            return pathSilver;
        }
    }
}
