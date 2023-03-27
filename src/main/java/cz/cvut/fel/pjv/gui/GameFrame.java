package cz.cvut.fel.pjv.gui;

import cz.cvut.fel.pjv.model.Game;
import cz.cvut.fel.pjv.pieces.Piece;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

public class GameFrame extends JFrame {

    private Game game;

    public GameFrame(Game game) {
        this.game = game;
        loadUI();
    }

    /**
     * Method to load window
     */
    public void loadUI() {
        JFrame gameFrame = new JFrame("Arimaa Game");
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setMinimumSize(new Dimension(720, 720));

        // add Components

        JPanel containerPanel = new JPanel(new BorderLayout(3, 3));
        containerPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        JToolBar tools = new JToolBar();

        tools.setFloatable(false);
        containerPanel.add(tools, BorderLayout.PAGE_START);
        tools.add(new JButton("New")); // TODO - create new game
        tools.add(new JButton("Save")); // TODO - save game
        tools.add(new JButton("Undo")); // TODO - go step back
        tools.add(new JButton("Resign")); // TODO - give up game
        tools.addSeparator();
        tools.add(new JLabel("Let's Play!!"));

        containerPanel.add(game.getBoardPanel());

        gameFrame.add(containerPanel);
        App.setFrameCenter(gameFrame);
        gameFrame.pack();
        gameFrame.setVisible(true);

    }



}
