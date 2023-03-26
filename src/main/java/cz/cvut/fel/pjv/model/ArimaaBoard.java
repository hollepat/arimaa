package cz.cvut.fel.pjv.model;


import cz.cvut.fel.pjv.pieces.Piece;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class ArimaaBoard {      // might extend JPanel
    // is Originator that holds it's current state

    private State currentState;
    private PlayerGold gold;
    private PlayerSilver silver;
    private List<Piece> pieces;

    public JPanel[][] arimaaBoardArray = new JPanel[8][8];




    /**
     * Constructor for Board.
     */
    public ArimaaBoard() {
        this.currentState = new State();
        this.gold = new PlayerGold();
        this.silver = new PlayerSilver();
    }

    /**
     * Save current State in Memento.
     *
     * @return Memento containing the State
     */
    public Memento save() {
        Memento memento = new Memento(currentState);
        return memento;
    }

    /**
     * Restore previous State from Memento at top of the Stack.
     *
     * @param memento Memento containing the State chessBoard will change to
     */
    public void restore(Memento memento) {
        this.currentState = memento.getState();
    }





}
