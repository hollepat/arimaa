package cz.cvut.fel.pjv.pieces;

import javax.swing.*;

// images in svg https://commons.wikimedia.org/wiki/Category:Arimaa_pieces_(SVG_set_by_Risteall)
abstract public class Piece extends JLabel {

    protected char positionX;     // a, b, c, d, e, f, g, h
    protected int positionY;      // 1, 2, 3, 4, 5, 6, 7, 8
    protected boolean alive;       // live = true, killed = false
    protected ColorPiece color;

    protected Type type;




    /**
     * Constructor for Piece
     * @param positionX     the x coordinates of Piece
     * @param positionY     the y coordinates of Piece
     * @param alive         the state whether if alive or not
     * @param color         color of the piece
     */
    public Piece(char positionX, int positionY, boolean alive, ColorPiece color) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.alive = alive;
        this.color = color;
    }


    public Piece(ColorPiece color, Type type) {
        this.color = color;
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public abstract String getImgPath();





}
