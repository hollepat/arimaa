package cz.cvut.fel.pjv.view;

import cz.cvut.fel.pjv.controller.Game;
import cz.cvut.fel.pjv.utils.MyTimer;
import cz.cvut.fel.pjv.utils.TimerEventListener;

import javax.swing.*;
import java.awt.*;
import java.time.LocalTime;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;

public class TimerPanel extends JPanel implements TimerEventListener {

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
        initialize();
        Game.logger.log(Level.CONFIG, "Timer was initiated.");
    }

    @Override
    public void tikTokGold(LocalTime currentTimeGold) {
        goldTimerDigitsLabel.setText(timers.getCurrentTimeGold().toString());
        goldTimerStatusPanel.setVisible(true);
        silverTimerStatusPanel.setVisible(false);
    }

    @Override
    public void tikTokSilver(LocalTime currentTimeSilver) {
        silverTimerDigitsLabel.setText(timers.getCurrentTimeSilver().toString());
        silverTimerStatusPanel.setVisible(true);
        goldTimerStatusPanel.setVisible(false);
    }

    public void silverTimerTikTok() {
        silverTimerDigitsLabel.setText(timers.getCurrentTimeSilver().toString());
        silverTimerStatusPanel.setVisible(true);
        goldTimerStatusPanel.setVisible(false);
    }

    public void goldTimerTikTok() {
        goldTimerDigitsLabel.setText(timers.getCurrentTimeGold().toString());
        goldTimerStatusPanel.setVisible(true);
        silverTimerStatusPanel.setVisible(false);
    }


    public void setSilverTimer(long time) {
        //silverTime.setTime(time);
        silverTimerDigitsLabel.setText(timers.getCurrentTimeSilver().toString());
        silverTimerStatusPanel.setVisible(false);
    }

    public void setGoldTimer(long time) {
        //goldTime.setTime(time);
        goldTimerDigitsLabel.setText(timers.getCurrentTimeGold().toString());
        goldTimerStatusPanel.setVisible(false);
    }

    private void initialize() {
        silverTimerDigitsLabel = new JLabel(timers.getCurrentTimeSilver().toString());
        silverTimerDigitsLabel.setFont(silverTimerDigitsLabel.getFont().deriveFont(48f));
        silverTimerDigitsPanel = new JPanel();
        silverTimerDigitsPanel.add(silverTimerDigitsLabel);
        silverTimerStatusPanel = new JPanel();
        silverTimerStatusPanel.setBackground(Color.GRAY);
        silverTimerPanel = new JPanel(new BorderLayout());
        silverTimerPanel.add(silverTimerDigitsPanel, BorderLayout.LINE_START);
        silverTimerPanel.add(silverTimerStatusPanel, BorderLayout.CENTER);
        silverTimerPanel.setBorder(BorderFactory.createTitledBorder("Silver"));

        goldTimerDigitsLabel = new JLabel(timers.getCurrentTimeGold().toString());
        goldTimerDigitsLabel.setFont(goldTimerDigitsLabel.getFont().deriveFont(48f));
        goldTimerDigitsPanel = new JPanel();
        goldTimerDigitsPanel.add(goldTimerDigitsLabel);
        goldTimerStatusPanel = new JPanel();
        goldTimerStatusPanel.setBackground(Color.YELLOW);
        goldTimerPanel = new JPanel(new BorderLayout());
        goldTimerPanel.add(goldTimerDigitsPanel, BorderLayout.LINE_START);
        goldTimerPanel.add(goldTimerStatusPanel, BorderLayout.CENTER);
        goldTimerPanel.setBorder(BorderFactory.createTitledBorder("Gold"));

        displayPanel = new JPanel(new GridLayout(2, 1));
        displayPanel.add(silverTimerPanel);
        displayPanel.add(goldTimerPanel);

        this.add(displayPanel, BorderLayout.CENTER);
        this.setPreferredSize(new Dimension(300, 200));
    }

}




