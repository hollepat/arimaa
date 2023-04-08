package cz.cvut.fel.pjv.model;

import cz.cvut.fel.pjv.pieces.ColorPiece;

public class Player {

    private ColorPiece color;
    private String name;

    public Player(ColorPiece color, String name) {
        this.color = color;
        this.name = name;
    }

    public Player(ColorPiece color) {
        this.color = color;
    }

    public ColorPiece getColor() {
        return color;
    }
}
