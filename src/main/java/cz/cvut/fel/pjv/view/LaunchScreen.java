package cz.cvut.fel.pjv.view;

import cz.cvut.fel.pjv.controller.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LaunchScreen extends JFrame {

    private JFileChooser fileChooser;
    private JButton newGameButton;
    private JButton loadGameButton;

    public LaunchScreen() {
        super("Arimaa");
        start();
    }

    private void start() {
        this.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);           // enable close button
        this.setMinimumSize(new Dimension(320, 240));      // set size of frame

        Container container = this.getContentPane();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        JLabel gameName = new JLabel("Welcome to Arimaa!");

        newGameButton = new JButton("New Game");
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SetUpScreen setUpScreen = new SetUpScreen();
                setUpScreen.start();
                setVisible(false);
                dispose();

            }
        });
        loadGameButton = new JButton("Load Game");
        fileChooser = new JFileChooser("Load Game!");
        loadGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fileChooser.showOpenDialog(LaunchScreen.this);
                Game game = new Game(null);
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
