package cz.cvut.fel.pjv.view;

import cz.cvut.fel.pjv.controller.Game;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class LaunchScreen extends JFrame {

    private JFileChooser fileChooser;

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

        JButton newGameButton = new JButton("New Game");
        newGameButton.addActionListener(e -> {
            SetUpScreen setUpScreen = new SetUpScreen();
            setUpScreen.start();
            setVisible(false);
            dispose();

        });
        JButton loadGameButton = new JButton("Load Game");
        fileChooser = new JFileChooser();
        // Set the default directory to open
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        // Set the dialog title
        fileChooser.setDialogTitle("Choose a file");
        loadGameButton.addActionListener(e -> {
            int userSelection = fileChooser.showOpenDialog(LaunchScreen.this);
            if (userSelection == JFileChooser.APPROVE_OPTION) { // if the user selects a file
                File fileToOpen = fileChooser.getSelectedFile(); // get the selected file
                Game game = new Game(fileToOpen);
            }
            setVisible(false);
            dispose();
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
