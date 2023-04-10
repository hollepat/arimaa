package cz.cvut.fel.pjv.pieces;

public class Elephant extends Piece{

    private final String pathSilver = "/pieces/elephant-silver.png";
    private final String pathGold = "/pieces/elephant-gold.png";

    /**
     * Constructor for Piece
     *
     * @param color of player, it belongs to
     */
    public Elephant(ColorPiece color) {
        super(color, PieceType.ELEPHANT);
        pieceStrength = 6;
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
