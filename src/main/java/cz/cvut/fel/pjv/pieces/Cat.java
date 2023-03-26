package cz.cvut.fel.pjv.pieces;

public class Cat extends Piece{

    private String pathSilver = "/pieces/cat-silver.png";
    private String pathGold = "/pieces/cat-gold.png";

    /**
     * Constructor for Piece
     *
     * @param positionX the x location of Piece
     * @param positionY the y location of Piece
     * @param alive     the state whether if alive or not
     */
    public Cat(char positionX, int positionY, boolean alive, ColorPiece color) {
        super(positionX, positionY, alive, color);
    }

    public Cat(ColorPiece color) {
        super(color, Type.CAT);
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
