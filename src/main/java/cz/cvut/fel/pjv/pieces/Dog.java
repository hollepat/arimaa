package cz.cvut.fel.pjv.pieces;

public class Dog extends Piece{

    private String pathSilver = "/pieces/dog-silver.png";
    private String pathGold = "/pieces/dog-gold.png";
    /**
     * Constructor for Piece
     *
     * @param positionX the x location of Piece
     * @param positionY the y location of Piece
     * @param alive     the state whether if alive or not
     */
    public Dog(char positionX, int positionY, boolean alive, ColorPiece color) {
        super(positionX, positionY, alive, color);
    }

    public Dog(ColorPiece color) {
        super(color, PieceType.DOG);
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
