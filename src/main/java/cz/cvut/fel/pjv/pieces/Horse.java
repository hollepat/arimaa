package cz.cvut.fel.pjv.pieces;

public class Horse extends Piece{
    private final String pathSilver = "/pieces/horse-silver.png";
    private final String pathGold = "/pieces/horse-gold.png";

    /**
     * Constructor for Piece
     *
     * @param color of player, it belongs to
     */
    public Horse(ColorPiece color) {
        super(color, PieceType.HORSE);
        pieceStrength = 4;
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
