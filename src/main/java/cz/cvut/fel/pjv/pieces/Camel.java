package cz.cvut.fel.pjv.pieces;

public class Camel extends Piece{

    private String pathSilver = "/pieces/camel-silver.png";
    private String pathGold = "/pieces/camel-gold.png";

    /**
     * Constructor for Piece
     *
     * @param color of player, it belongs to
     */
    public Camel(ColorPiece color) {
        super(color, PieceType.CAMEL);
        pieceStrength = 5;
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
