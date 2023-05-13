import cz.cvut.fel.pjv.utils.MyTimer;
import org.junit.jupiter.api.Test;

public class MyTimerTest {

    @Test
    public void CreateTest() {
        MyTimer timers = new MyTimer(true, 20);
        Thread timersThread = new Thread(timers);
        timersThread.start();
    }


    @Test
    public void RunTest() {
        MyTimer timers = new MyTimer(true, 20);
        Thread timersThread = new Thread(timers);
        timersThread.start();

        // run both timers for 5 seconds
        timers.playGold();
        timers.playSilver();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            // handle exception
        }

        // pause timer 1 for 5 seconds
        timers.pauseGold();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            // handle exception
        }
        timers.playGold();

        // stop timer 2
        timers.stopSilver();
        try {
            timersThread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
