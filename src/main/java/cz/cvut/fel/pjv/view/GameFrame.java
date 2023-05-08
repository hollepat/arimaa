package cz.cvut.fel.pjv.view;

import cz.cvut.fel.pjv.controller.Game;
import cz.cvut.fel.pjv.controller.GameStatus;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
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
        //add(game.getTimerPanel(), BorderLayout.EAST);

        App.setFrameCenter(this);
        setVisible(true);
        pack();


    }

    private void initToolBar() {

        // --- Create Components
        JToolBar tools = new JToolBar();
        tools.setFloatable(false);
        JButton newGame = new JButton("New");
        newGame.addActionListener(e -> {
            LaunchScreen launchScreen = new LaunchScreen();
            dispose();
        });
        JButton save = new JButton("Save");
        save.addActionListener(e -> {
            try {
                game.saveToFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        JButton undoButton = new JButton("Undo");
        undoButton.addActionListener(actionEvent -> {
            game.undoTurn();
        });
        JButton endTurn = new JButton("End Turn");
        endTurn.addActionListener(e -> {
                if (game.moveCnt >= 1) {
                    game.endTurn();
                } else {
                    Game.logger.log(Level.WARNING, "Your turn must have at least 1 move!");
                    JOptionPane.showMessageDialog(null, "Your turn must have at least 1 move!");
                }
            }
        );
        JButton play = new JButton("Play!");
        play.addActionListener(e -> {
            game.setGameStatus(GameStatus.ACTIVE);
            if (game.getCurrentPlayer() == game.getNpcPlayer()) {
                game.moveRequestPC();
            }
            infoText.setText("Current Player is: " + game.getCurrentPlayer().getColor());
        });
        infoText = new JButton("Pres Play to start a Game!");
        infoText.setBorderPainted(false);

        // --- Add Components ---
        add(tools, BorderLayout.NORTH);
        tools.add(newGame);
        tools.add(save);
        tools.add(undoButton);
        tools.add(endTurn);
        tools.add(play);
        tools.addSeparator();
        tools.add(infoText);



    }

    /**
     * Change text in JButton infoText.
     *
     * @param str is new msg for infoText.
     */
    public void changeMsg(String str) {
        infoText.setText(str);
    }




}
