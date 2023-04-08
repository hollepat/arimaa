package cz.cvut.fel.pjv.pieces;

public class Rabbit extends Piece{

    private String pathSilver = "/pieces/rabbit-silver.png";
    private String pathGold = "/pieces/rabbit-gold.png";


    /**
     * Constructor for Piece
     *
     * @param color of player, it belongs to
     */
    public Rabbit(ColorPiece color) {
        super(color, PieceType.RABBIT);
        pieceStrength = 1;
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
