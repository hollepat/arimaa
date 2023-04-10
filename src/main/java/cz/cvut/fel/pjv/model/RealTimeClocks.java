package cz.cvut.fel.pjv.model;

import cz.cvut.fel.pjv.gui.TimerPanel;

public class RealTimeClocks extends Thread{

    private TimerPanel timerPanel;
    private int timeLimit;

    public RealTimeClocks(TimerPanel timerPanel, int timeLimit) {
        this.timerPanel = timerPanel;
        this.timeLimit = timeLimit;
    }

    /**
     * Update in timePanel time.
     */
    @Override
    public void interrupt() {
        super.interrupt();
    }

    /**
     * Start thread.
     */
    @Override
    public void start() {
        super.start();
    }
}
