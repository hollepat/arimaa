package cz.cvut.fel.pjv.pieces;

public class Cat extends Piece{

    private final String pathSilver = "/pieces/cat-silver.png";
    private final String pathGold = "/pieces/cat-gold.png";

    /**
     * Constructor for Piece
     *
     * @param color of player, it belongs to
     */
    public Cat(ColorPiece color) {
        super(color, PieceType.CAT);
        pieceStrength = 2;
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
