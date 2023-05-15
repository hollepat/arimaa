package cz.cvut.fel.pjv.view;

import cz.cvut.fel.pjv.controller.Game;
import cz.cvut.fel.pjv.utils.MyTimer;
import cz.cvut.fel.pjv.utils.TimerEventListener;

import javax.swing.*;
import java.awt.*;
import java.time.LocalTime;
import java.util.logging.Level;

public class TimerPanel extends JPanel implements TimerEventListener {

    private final MyTimer timers;
    private JLabel silverTimerDigitsLabel;
    private JPanel silverTimerStatusPanel;
    private JLabel goldTimerDigitsLabel;

    private JPanel goldTimerStatusPanel;

    /**
     * Constructor for TimerPanel.
     * @param game reference to Game object
     */
    public TimerPanel(Game game) {
        super(new BorderLayout());
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


    public void setSilverTimer() {
        silverTimerDigitsLabel.setText(timers.getCurrentTimeSilver().toString());
        silverTimerStatusPanel.setVisible(false);
    }

    public void setGoldTimer() {
        goldTimerDigitsLabel.setText(timers.getCurrentTimeGold().toString());
        goldTimerStatusPanel.setVisible(false);
    }

    private void initialize() {
        silverTimerDigitsLabel = new JLabel(timers.getCurrentTimeSilver().toString());
        silverTimerDigitsLabel.setFont(silverTimerDigitsLabel.getFont().deriveFont(48f));
        JPanel silverTimerDigitsPanel = new JPanel();
        silverTimerDigitsPanel.add(silverTimerDigitsLabel);
        silverTimerStatusPanel = new JPanel();
        silverTimerStatusPanel.setBackground(Color.GRAY);
        JPanel silverTimerPanel = new JPanel(new BorderLayout());
        silverTimerPanel.add(silverTimerDigitsPanel, BorderLayout.LINE_START);
        silverTimerPanel.add(silverTimerStatusPanel, BorderLayout.CENTER);
        silverTimerPanel.setBorder(BorderFactory.createTitledBorder("Silver"));

        goldTimerDigitsLabel = new JLabel(timers.getCurrentTimeGold().toString());
        goldTimerDigitsLabel.setFont(goldTimerDigitsLabel.getFont().deriveFont(48f));
        JPanel goldTimerDigitsPanel = new JPanel();
        goldTimerDigitsPanel.add(goldTimerDigitsLabel);
        goldTimerStatusPanel = new JPanel();
        goldTimerStatusPanel.setBackground(Color.YELLOW);
        JPanel goldTimerPanel = new JPanel(new BorderLayout());
        goldTimerPanel.add(goldTimerDigitsPanel, BorderLayout.LINE_START);
        goldTimerPanel.add(goldTimerStatusPanel, BorderLayout.CENTER);
        goldTimerPanel.setBorder(BorderFactory.createTitledBorder("Gold"));

        JPanel displayPanel = new JPanel(new GridLayout(2, 1));
        displayPanel.add(silverTimerPanel);
        displayPanel.add(goldTimerPanel);

        this.add(displayPanel, BorderLayout.CENTER);
        this.setPreferredSize(new Dimension(300, 200));
    }

}




