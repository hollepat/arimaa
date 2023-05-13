package cz.cvut.fel.pjv.view;

import cz.cvut.fel.pjv.controller.Game;
import cz.cvut.fel.pjv.utils.MyTimer;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;

public class TimerPanel extends JPanel implements Observer {

    private Game game;
    private MyTimer timers;

    private JPanel displayPanel;
    private JPanel silverTimerPanel;
    private JPanel silverTimerDigitsPanel;
    private JLabel silverTimerDigitsLabel;
    private JPanel silverTimerStatusPanel;
    private JPanel goldTimerPanel;
    private JPanel goldTimerDigitsPanel;
    private JLabel goldTimerDigitsLabel;

    private JPanel goldTimerStatusPanel;


    public TimerPanel(Game game) {
        super(new BorderLayout());
        this.game = game;
        timers = game.getTimers();
        //initialize();
        Game.logger.log(Level.CONFIG, "Timer was initiated.");
    }

    @Override
    public void update(Observable o, Object arg) {

    }

//    public void silverTimerTikTok() {
//        pause(goldThread);
//        start(silverThread);
//        silverTimerDigitsLabel.setText(silverTimer.toString());
//        silverTimerStatusPanel.setVisible(true);
//        goldTimerStatusPanel.setVisible(false);
//    }
//
//    public void goldTimerTikTok() {
//        pause(silverThread);
//        start(goldThread);
//        goldTimerDigitsLabel.setText(goldTimer.toString());
//        goldTimerStatusPanel.setVisible(true);
//        silverTimerStatusPanel.setVisible(false);
//    }
//
//    public void start(Thread t) {
//        if (!t.isAlive()) {
//            t.start();
//        } else {
//            t.notify();
//        }
//    }
//
//    public void pause(Thread t) {
//        try {
//            t.wait();
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public void switchTimer(ColorPiece switchTo) {
//        switch (switchTo) {
//            case GOLD:
//                pause(silverThread);
//                start(goldThread);
//                break;
//            case SILVER:
//                pause(goldThread);
//                start(silverThread);
//        };
//    }
//
//    public void setSilverTimer(long time) {
//        //silverTime.setTime(time);
//        silverTimerDigitsLabel.setText(silverTimer.toString());
//        silverTimerStatusPanel.setVisible(false);
//    }
//
//    public void setGoldTimer(long time) {
//        //goldTime.setTime(time);
//        goldTimerDigitsLabel.setText(goldTimer.toString());
//        goldTimerStatusPanel.setVisible(false);
//    }
//
//    private void initialize() {
//        silverTimerDigitsLabel = new JLabel(silverTimer.toString());
//        silverTimerDigitsLabel.setFont(silverTimerDigitsLabel.getFont().deriveFont(48f));
//        silverTimerDigitsPanel = new JPanel();
//        silverTimerDigitsPanel.add(silverTimerDigitsLabel);
//        silverTimerStatusPanel = new JPanel();
//        silverTimerStatusPanel.setBackground(Color.GRAY);
//        silverTimerPanel = new JPanel(new BorderLayout());
//        silverTimerPanel.add(silverTimerDigitsPanel, BorderLayout.LINE_START);
//        silverTimerPanel.add(silverTimerStatusPanel, BorderLayout.CENTER);
//        silverTimerPanel.setBorder(BorderFactory.createTitledBorder("Silver"));
//
//        goldTimerDigitsLabel = new JLabel(goldTimer.toString());
//        goldTimerDigitsLabel.setFont(goldTimerDigitsLabel.getFont().deriveFont(48f));
//        goldTimerDigitsPanel = new JPanel();
//        goldTimerDigitsPanel.add(goldTimerDigitsLabel);
//        goldTimerStatusPanel = new JPanel();
//        goldTimerStatusPanel.setBackground(Color.YELLOW);
//        goldTimerPanel = new JPanel(new BorderLayout());
//        goldTimerPanel.add(goldTimerDigitsPanel, BorderLayout.LINE_START);
//        goldTimerPanel.add(goldTimerStatusPanel, BorderLayout.CENTER);
//        goldTimerPanel.setBorder(BorderFactory.createTitledBorder("Gold"));
//
//        displayPanel = new JPanel(new GridLayout(2, 1));
//        displayPanel.add(silverTimerPanel);
//        displayPanel.add(goldTimerPanel);
//
//        this.add(displayPanel, BorderLayout.CENTER);
//        this.setPreferredSize(new Dimension(300, 200));
//    }

}




