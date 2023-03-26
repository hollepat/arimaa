package cz.cvut.fel.pjv.pieces;

public class Elephant extends Piece{

    private String pathSilver = "/pieces/elephant-silver.png";
    private String pathGold = "/pieces/elephant-gold.png";

    /**
     * Constructor for Piece
     *
     * @param positionX the x location of Piece
     * @param positionY the y location of Piece
     * @param alive     the state whether if alive or not
     */
    public Elephant(char positionX, int positionY, boolean alive, ColorPiece color) {
        super(positionX, positionY, alive, color);
    }

    public Elephant(ColorPiece color) {
        super(color, Type.ELEPHANT);
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
