package cz.cvut.fel.pjv.gui;

import cz.cvut.fel.pjv.model.Game;
import javax.swing.*;
import java.awt.*;
import java.util.logging.Level;

public class GameFrame extends JFrame {

    private final Game game;
    private JButton infoText;

    public GameFrame(Game game) {
        this.game = game;
        loadUI();
    }

    /**
     * Method to load Game window.
     */
    public void loadUI() {
        setTitle("Arimaa Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(720, 720));
        setLayout(new BorderLayout(3, 3));

        initToolBar();
        add(game.getBoardPanel(), BorderLayout.CENTER);

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
        endTurn.addActionListener(e -> {
                if (game.movesInTurn >= 1) {
                    game.switchCurrentPlayer();
                } else {
                    Game.logger.log(Level.WARNING, "Your turn must have at least 1 move!");
                }
            }
        );
        infoText = new JButton("Current player is: GOLD");
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


    public void changeMsg(String str) {
        infoText.setText(str);
    }




}
