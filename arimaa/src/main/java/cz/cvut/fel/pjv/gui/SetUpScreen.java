package cz.cvut.fel.pjv.gui;

import cz.cvut.fel.pjv.model.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SetUpScreen {
    // TODO screen that will take all the parameters to set up game and create Game !!!

    public void start() {
        JFrame setUpFrame = new JFrame("Arimaa");
        setUpFrame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        setUpFrame.setMinimumSize(new Dimension(320, 240));       // set size of frame
        Container container = setUpFrame.getContentPane();
        container.setLayout(new GridLayout());

        JButton createGame = new JButton("Create Game!");

        createGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setUpFrame.dispose();
                Game game = new Game();
                game.startGame();
            }
        });


        container.add(createGame);
        App.setFrameCenter(setUpFrame);
        setUpFrame.pack();
        setUpFrame.setVisible(true);
    }
}
