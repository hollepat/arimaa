package cz.cvut.fel.pjv.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LaunchScreen extends JFrame {

    public LaunchScreen() {
        super("Arimaa");
        start();
    }

    public void start() {
        this.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);           // enable close button
        this.setMinimumSize(new Dimension(320, 240));      // set size of frame

        Container container = this.getContentPane();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        JLabel gameName = new JLabel("Welcome to Arimaa!");

        JButton newGameButton = new JButton("New Game");
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SetUpScreen setUpScreen = new SetUpScreen();
                setUpScreen.start();
                setVisible(false);
                dispose();

            }
        });
        JButton loadGameButton = new JButton("Load Game");
        loadGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO find in folder system file
                // TODO Game game = new Game()
                // TODO call function to recreate game
                setVisible(false);
                dispose();
            }
        });

        gameName.setAlignmentX(Component.CENTER_ALIGNMENT);
        newGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loadGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        container.add(Box.createRigidArea(new Dimension(0, 30)));
        container.add(gameName);
        container.add(Box.createRigidArea(new Dimension(0, 70)));
        container.add(newGameButton);
        container.add(loadGameButton);

        App.setFrameCenter(this);
        this.pack();
        this.setVisible(true);

    }

    public static void main(String[] args) {
        LaunchScreen launchScreen = new LaunchScreen();
    }

}
