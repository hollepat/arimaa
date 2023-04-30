package cz.cvut.fel.pjv.gui;

import cz.cvut.fel.pjv.model.Game;

import javax.swing.*;
import java.awt.event.*;
import java.text.ParseException;

public class SetUpScreen extends JFrame {
    private JPanel contentPane;
    private JButton createButton;
    private JButton backButton;
    private JRadioButton playerVsPlayerRadioButton;
    private JRadioButton playerVsPCRadioButton;
    private JCheckBox logMessagesCheckBox;
    private JSpinner spinnerTimer;
    private JCheckBox setTimeLimit;
    private JCheckBox ownLayout;

    private JFrame launchFrame;

    public SetUpScreen() {

        setContentPane(contentPane);
        getRootPane().setDefaultButton(createButton);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        logMessagesCheckBox.setSelected(true);
        spinnerTimer.setEnabled(false);
        setListeners();

    }


    private void onCreate() {
        Game game = new Game(logMessagesCheckBox.isSelected(), (int) spinnerTimer.getValue(), ownLayout.isSelected());
        dispose();
    }

    private void onBack() {
        setVisible(false);
        LaunchScreen launchScreen = new LaunchScreen();
        dispose();
    }


    public void start() {
        this.pack();
        App.setFrameCenter(this);
        this.setVisible(true);
    }

    private void setListeners() {

        setTimeLimit.addActionListener(e -> {
            if (setTimeLimit.isSelected()) {
                spinnerTimer.setEnabled(true);
            } else {
                spinnerTimer.setEnabled(false);
            }

        });
        spinnerTimer.addChangeListener(e -> {
            try {
                spinnerTimer.commitEdit();
            } catch (ParseException ex) {
                throw new RuntimeException(ex);
            }
        });
        createButton.addActionListener(e -> onCreate());

        backButton.addActionListener(e -> onBack());

        // call onBack() on ESCAPE
        contentPane.registerKeyboardAction(e -> onBack(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        // call onBack() when cross is clicked
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onBack();
            }
        });
    }
}
