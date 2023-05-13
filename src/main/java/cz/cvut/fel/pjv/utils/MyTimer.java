package cz.cvut.fel.pjv.utils;

import cz.cvut.fel.pjv.controller.GameStatus;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MyTimer implements Runnable {
    private volatile boolean runningGold = true;
    private volatile boolean runningSilver = true;
    private volatile boolean pausedGold = true;
    private volatile boolean pausedSilver = true;

    private LocalTime startTime;
    private LocalTime endTime;
    private LocalTime currentTimeGold;
    private LocalTime currentTimeSilver;
    private GameStatus gameStatus;

    public static Logger logger = Logger.getLogger(MyTimer.class.getName());
    public static final Level level = Level.FINE;

    public MyTimer(Boolean isLogging, int timeLimit) {
        setUpLogger(isLogging);
        // set start time to 00:00:00
        startTime = LocalTime.MIN;
        currentTimeGold = startTime;
        currentTimeSilver = startTime;

        // print start time
        logger.log(Level.INFO, "Start time: " + startTime.toString());

        // calculate end time based on time limit
        int timeLimitSeconds = timeLimit;
        endTime = startTime.plusSeconds(timeLimitSeconds);

        // print end time
        logger.log(Level.INFO, "End time: " + endTime.toString());

        gameStatus = GameStatus.ACTIVE;
    }

    @Override
    public void run() {

        while ((runningGold || runningSilver) && gameStatus == GameStatus.ACTIVE) {
            if (!pausedGold && runningGold) {

                // get current time for timer1
                currentTimeGold = currentTimeGold.plusSeconds(1);

                // format time as a string
                String timeStringGold = currentTimeGold.format(DateTimeFormatter.ofPattern("HH:mm:ss"));

                // print time to console
                logger.log(Level.INFO,"Gold Timer: " + timeStringGold);

                // check if time's up
                if (currentTimeGold.equals(endTime)) {
                    gameStatus = GameStatus.SILVER_WIN;
                    logger.log(Level.INFO, "Time's up for Gold");
                }
            }

            if (!pausedSilver && runningSilver) {
                // get current time for timer2
                currentTimeSilver = currentTimeSilver.plusSeconds(1);

                // format time as a string
                String timeStringSilver = currentTimeSilver.format(DateTimeFormatter.ofPattern("HH:mm:ss"));

                // print time to console
                logger.log(Level.INFO, "Silver Timer: " + timeStringSilver);

                // check if time's up --> break while()
                if (currentTimeSilver.equals(endTime)) {
                    gameStatus = GameStatus.GOLD_WIN;
                    logger.log(Level.INFO, "Time's up for Silver");
                }

            }

            // wait for one second before printing the next time --> effect of counting after seconds
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // handle exception
                e.printStackTrace();
            }
        }
    }

    private void setUpLogger(Boolean isLogging) {
        logger.setUseParentHandlers(false);
        Handler handler = new ConsoleHandler();
        handler.setFormatter(new MyFormatter());
        logger.addHandler(handler);

        if (isLogging) {
            logger.setLevel(level);
            handler.setLevel(level);
        } else {
            logger.setLevel(Level.OFF);
        }
    }

    /**
     * Finish Timer.
     */
    public void stop() {
        runningGold = false;
        runningSilver = false;
    }
    /**
     * End Gold Timer completely.
     */
    public void stopGold() {
        runningGold = false;
    }

    /**
     * End Silver Timer completely.
     */
    public void stopSilver() {
        runningSilver = false;
    }

    /**
     * Pause Gold Timer until it is not resumed.
     */
    public void pauseGold() {
        pausedGold = true;
    }

    /**
     * Pause Silver Timer until it is not resumed.
     */
    public void pauseSilver() {
        pausedSilver = true;
    }

    /**
     * Continue run Gold Timer or Start it.
     */
    public void playGold() {
        pausedGold = false;

    }

    /**
     * Continue run Silver Timer or Start it.
     */
    public void playSilver() {
        pausedSilver = false;
    }

}

