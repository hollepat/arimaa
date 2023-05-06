package cz.cvut.fel.pjv.pieces;

public class Rabbit extends Piece{

    private String pathSilver = "/pieces/rabbit-silver.png";
    private String pathGold = "/pieces/rabbit-gold.png";

    private char silverCharName = 'r';
    private char goldCharName = 'R';

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
    public char getNotationName() {
        return switch (color) {
            case GOLD -> goldCharName;
            case SILVER -> silverCharName;
        };
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
