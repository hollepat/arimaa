package cz.cvut.fel.pjv.pieces;

public class Elephant extends Piece{

    private final String pathSilver = "/pieces/elephant-silver.png";
    private final String pathGold = "/pieces/elephant-gold.png";

    private final char silverCharName = 'e';
    private final char goldCharName = 'E';

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
