package cz.cvut.fel.pjv.pieces;

public class Dog extends Piece{

    private final String pathSilver = "/pieces/dog-silver.png";
    private final String pathGold = "/pieces/dog-gold.png";
    private final char silverCharName = 'd';
    private final char goldCharName = 'D';


    /**
     * Constructor for Piece
     *
     * @param color of player, it belongs to
     */
    public Dog(ColorPiece color) {
        super(color, PieceType.DOG);
        pieceStrength = 3;
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
