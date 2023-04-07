package cz.cvut.fel.pjv.gui;

import cz.cvut.fel.pjv.model.Game;
import cz.cvut.fel.pjv.model.GameStatus;
import cz.cvut.fel.pjv.pieces.ColorPiece;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;

public class GameFrame extends JFrame {

    private final Game game;

    public GameFrame(Game game) {
        this.game = game;
        loadUI();
    }

    /**
     * Method to load Game window.
     */
    public void loadUI() {
        //JFrame gameFrame = new JFrame("Arimaa Game");
        //gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //gameFrame.setMinimumSize(new Dimension(720, 720));
        setTitle("Arimaa Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(720, 720));
        setLayout(new BorderLayout(3, 3));
        //setBounds(10, 10, 720, 720);

        // add Components
        // TODO change layout handling
        //JPanel containerPanel = new JPanel(new BorderLayout(3, 3));
        //containerPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

        initToolBar();

        //containerPanel.add(game.getBoardPanel());
        add(game.getBoardPanel(), BorderLayout.CENTER);

        /*gameFrame.add(containerPanel);
        App.setFrameCenter(gameFrame);
        gameFrame.pack();
        gameFrame.setVisible(true);*/
        App.setFrameCenter(this);
        setVisible(true);
        pack();


    }

    private void initToolBar() {

        // --- Create Components
        JToolBar tools = new JToolBar();
        tools.setFloatable(false);
        JButton undoButton = new JButton("Undo");
        undoButton.addActionListener(actionEvent -> {
            game.undoMove();
        });
        JButton endTurn = new JButton("End turn!");
        endTurn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch (game.currentPlayer.getColor()) {
                    case GOLD -> game.currentPlayer = game.getPlayerSilver();
                    case SILVER -> game.currentPlayer = game.getPlayerGold();
                    default -> Game.logger.log(Level.WARNING, "Current player is null!");
                }
            }
        });
        JButton infoText = new JButton("Let's Play!!");
        infoText.setBorderPainted(false);

        // --- Add Components ---
        add(tools, BorderLayout.NORTH);
        tools.add(new JButton("New")); // TODO - create new game
        tools.add(new JButton("Save")); // TODO - save game
        tools.add(undoButton);
        tools.add(endTurn); // TODO - end turn of current player
        tools.addSeparator();
        tools.add(infoText);

    }


}
