package cz.cvut.fel.pjv.pieces;

public class Camel extends Piece{

    private String pathSilver = "/pieces/camel-silver.png";
    private String pathGold = "/pieces/camel-gold.png";

    /**
     * Constructor for Piece
     *
     * @param positionX the x location of Piece
     * @param positionY the y location of Piece
     * @param alive     the state whether if alive or not
     */
    public Camel(char positionX, int positionY, boolean alive, ColorPiece color) {
        super(positionX, positionY, alive, color);
    }

    public Camel(ColorPiece color) {
        super(color, Type.CAMEL);
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
