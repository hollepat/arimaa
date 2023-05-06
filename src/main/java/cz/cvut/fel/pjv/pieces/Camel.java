package cz.cvut.fel.pjv.pieces;

public class Camel extends Piece{

    private final String pathSilver = "/pieces/camel-silver.png";
    private final String pathGold = "/pieces/camel-gold.png";

    private char silverCharName = 'm';
    private char goldCharName = 'M';

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
