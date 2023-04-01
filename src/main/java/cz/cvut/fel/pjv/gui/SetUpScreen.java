package cz.cvut.fel.pjv.gui;

import cz.cvut.fel.pjv.model.Game;

import javax.swing.*;
import java.awt.event.*;

public class SetUpScreen extends JFrame {
    private JPanel contentPane;
    private JButton createButton;
    private JButton backButton;
    private JRadioButton playerVsPlayerRadioButton;
    private JRadioButton playerVsPCRadioButton;
    private JCheckBox logMessagesCheckBox;

    private JFrame launchFrame;

    public SetUpScreen() {

        setContentPane(contentPane);
        getRootPane().setDefaultButton(createButton);

        createButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCreate();
            }
        });

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onBack();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onBack();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onBack();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

    }

    private void onCreate() {
        Game game = new Game(logMessagesCheckBox.isSelected());
        dispose();
    }

    private void onBack() {
        dispose();
    }


    public void start() {
        this.pack();
        App.setFrameCenter(this);
        this.setVisible(true);
    }

}
