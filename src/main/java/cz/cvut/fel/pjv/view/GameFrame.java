package cz.cvut.fel.pjv.view;

import cz.cvut.fel.pjv.controller.Game;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
        JButton save = new JButton("Save");
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.saveToFile();
            }
        });
        JButton undoButton = new JButton("Undo");
        undoButton.addActionListener(actionEvent -> {
            //game.undoMove();
            game.undoTurn();
        });
        JButton endTurn = new JButton("End turn");
        endTurn.addActionListener(e -> {
                if (game.moveCnt >= 1) {
                    //game.switchCurrentPlayer();
                    game.endTurn();
                } else {
                    Game.logger.log(Level.WARNING, "Your turn must have at least 1 move!");
                }
            }
        );
        JButton setLayout = new JButton("Set Layout");
        setLayout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO
            }
        });
        infoText = new JButton("Current player is: GOLD");
        infoText.setBorderPainted(false);

        // --- Add Components ---
        add(tools, BorderLayout.NORTH);
        tools.add(newGame); // TODO - create new game
        tools.add(save); // TODO - save game
        tools.add(undoButton);
        tools.add(endTurn);
        tools.add(setLayout);
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
