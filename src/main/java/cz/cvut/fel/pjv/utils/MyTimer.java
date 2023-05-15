package cz.cvut.fel.pjv.utils;

import cz.cvut.fel.pjv.controller.Game;
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

    private final LocalTime endTime;
    private LocalTime currentTimeGold;
    private LocalTime currentTimeSilver;
    private GameStatus gameStatus;
    private TimerEventListener listener;
    private final Game game;

    public static Logger logger = Logger.getLogger(MyTimer.class.getName());
    public static final Level level = Level.FINE;

    /**
     * Contructor for MyTimer.
     * @param isLogging is boolean, true means print log messages
     * @param timeLimit is limit for players
     * @param game reference to instance of Game
     */
    public MyTimer(Boolean isLogging, int timeLimit, Game game) {
        setUpLogger(isLogging);
        // set start time to 00:00:00
        LocalTime startTime = LocalTime.MIN;
        currentTimeGold = startTime;
        currentTimeSilver = startTime;

        // print start time
        logger.log(Level.INFO, "Start time: " + startTime.toString());

        // calculate end time based on time limit
        endTime = startTime.plusSeconds(timeLimit);

        // print end time
        logger.log(Level.INFO, "End time: " + endTime.toString());

        // set game status
        gameStatus = GameStatus.ACTIVE;
        this.game = game;
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

                // notify listener
                if (listener != null) {
                    listener.tikTokGold(currentTimeGold);
                }

                // check if time's up
                if (currentTimeGold.equals(endTime)) {
                    gameStatus = GameStatus.SILVER_WIN;
                    updateGameStatusInGameControl(GameStatus.SILVER_WIN);
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

                // notify listener
                if (listener != null) {
                    listener.tikTokSilver(currentTimeSilver);
                }

                // check if time's up --> break while()
                if (currentTimeSilver.equals(endTime)) {
                    gameStatus = GameStatus.GOLD_WIN;
                    updateGameStatusInGameControl(GameStatus.GOLD_WIN);
                    logger.log(Level.INFO, "Time's up for Silver");
                }
            }

            // wait for one second before printing the next time --> effect of counting after seconds
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
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
     * Change game status in Game object.
     * @param gameStatus is new status of Game
     */
    private void updateGameStatusInGameControl(GameStatus gameStatus) {
        if (game != null) {
            synchronized (game) {
                game.setGameStatus(gameStatus);
            }
        }
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

    public LocalTime getCurrentTimeGold() {
        return currentTimeGold;
    }

    public LocalTime getCurrentTimeSilver() {
        return currentTimeSilver;
    }

    public String getCurrentTimeGoldString() {
        return currentTimeGold.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }

    public String getCurrentTimeSilverString() {
        return currentTimeSilver.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }

    public void setCurrentTimeGold(LocalTime currentTimeGold) {
        this.currentTimeGold = currentTimeGold;
        logger.log(Level.INFO, "Gold's time set to " + currentTimeGold.format(DateTimeFormatter.ofPattern("HH:mm:ss")));
    }

    public void setCurrentTimeSilver(LocalTime currentTimeSilver) {
        this.currentTimeSilver = currentTimeSilver;
        logger.log(Level.INFO, "Silver's time set to " + currentTimeGold.format(DateTimeFormatter.ofPattern("HH:mm:ss")));
    }

    public void setListener(TimerEventListener listener) {
        this.listener = listener;
    }
}

