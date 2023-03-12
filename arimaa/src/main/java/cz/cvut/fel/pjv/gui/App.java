package cz.cvut.fel.pjv.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class App {


    public void start() {
        JFrame frame = new JFrame("Arimaa");
        frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(320, 240));       // set size of frame
        Container container = frame.getContentPane();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        JLabel gameName = new JLabel("Arimaa");

        JButton newGameButton = new JButton("New Game");
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO open new window setting up new game
                // TODO close the main window
                SetUpScreen setUpScreen = new SetUpScreen();
                frame.dispose();
                setUpScreen.start();


            }
        });
        JButton loadGameButton = new JButton("Load Game");
        loadGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO find in folder system old game
                // TODO close the main window
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

        setFrameCenter(frame);
        frame.pack();
        frame.setVisible(true);

    }

    private void setFrameCenter(JFrame frame) {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
    }

    public static void main(String[] args) {
        App appGui = new App();
        appGui.start();

    }
}
