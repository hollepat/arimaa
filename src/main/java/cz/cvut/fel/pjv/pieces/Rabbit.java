package cz.cvut.fel.pjv.pieces;

public class Rabbit extends Piece{

    private String pathSilver = "/pieces/rabbit-silver.png";
    private String pathGold = "/pieces/rabbit-gold.png";


    /**
     * Constructor for Piece
     *
     * @param positionX the x location of Piece
     * @param positionY the y location of Piece
     * @param alive the state whether if alive or not
     */
    public Rabbit(char positionX, int positionY, boolean alive, ColorPiece color) {
        super(positionX, positionY, alive, color);
    }

    public Rabbit(ColorPiece color) {
        super(color, PieceType.RABBIT);
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
