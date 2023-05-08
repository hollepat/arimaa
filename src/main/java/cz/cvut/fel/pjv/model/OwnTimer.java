package cz.cvut.fel.pjv.model;

import cz.cvut.fel.pjv.utils.MyFormatter;

import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OwnTimer implements Runnable{

    private final int timeLimit;

    public static Logger logger = Logger.getLogger(OwnTimer.class.getName());
    public static final Level level = Level.FINE;

    /**
     * Constructor for RealTimeClocks.
     * @param timeLimit in seconds
     */
    public OwnTimer(int timeLimit) {
        setUpLogger();
        this.timeLimit = timeLimit;
    }

    @Override
    public void run() {
        try {
            for (int i = timeLimit; i >= 0; i--) {
                OwnTimer.logger.log(Level.FINE, "Time remaining: " + i);
                Thread.sleep(1000);
            }
            OwnTimer.logger.log(Level.INFO, "Time's up!");
        } catch (InterruptedException e) {
            OwnTimer.logger.log(Level.INFO,"Timer interrupted");
        }
    }

    private void setUpLogger() {
        logger.setUseParentHandlers(false);
        Handler handler = new ConsoleHandler();
        handler.setFormatter(new MyFormatter());
        logger.addHandler(handler);

        if (true) {
            logger.setLevel(level);
            handler.setLevel(level);
        } else {
            logger.setLevel(Level.OFF);
        }
    }

}
