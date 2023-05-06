package cz.cvut.fel.pjv.pieces;

public class Horse extends Piece{
    private final String pathSilver = "/pieces/horse-silver.png";
    private final String pathGold = "/pieces/horse-gold.png";

    private char silverCharName = 'h';
    private char goldCharName = 'H';

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
