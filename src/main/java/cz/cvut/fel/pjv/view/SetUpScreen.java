package cz.cvut.fel.pjv.view;

import cz.cvut.fel.pjv.controller.Game;

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
    private JRadioButton NPCIsGOLDRadioButton;
    private JRadioButton NPCIsSILVERRadioButton;

    private JFrame launchFrame;

    public SetUpScreen() {

        setContentPane(contentPane);
        getRootPane().setDefaultButton(createButton);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        ButtonGroup group = new ButtonGroup();
        group.add(NPCIsSILVERRadioButton);
        group.add(NPCIsGOLDRadioButton);

        logMessagesCheckBox.setSelected(true);
        spinnerTimer.setEnabled(false);
        spinnerTimer.setValue(0);
        setListeners();

    }


    private void onCreate() {
        if (playerVsPlayerRadioButton.isSelected()) {
            Game game = new Game(logMessagesCheckBox.isSelected(), (int) spinnerTimer.getValue(), ownLayout.isSelected());
        } else if (playerVsPCRadioButton.isSelected()) {
            Game game = new Game(logMessagesCheckBox.isSelected(), (int) spinnerTimer.getValue(), ownLayout.isSelected(), NPCIsGOLDRadioButton.isSelected(), NPCIsSILVERRadioButton.isSelected());
        }
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

        playerVsPCRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (playerVsPCRadioButton.isSelected()) {
                    NPCIsGOLDRadioButton.setEnabled(true);
                    NPCIsSILVERRadioButton.setEnabled(true);
                }
            }
        });

        playerVsPlayerRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (playerVsPlayerRadioButton.isSelected()) {
                    NPCIsGOLDRadioButton.setEnabled(false);
                    NPCIsSILVERRadioButton.setEnabled(false);
                }
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
