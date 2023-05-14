import cz.cvut.fel.pjv.utils.MyTimer;
import org.junit.jupiter.api.Test;

public class MyTimerTest {

    @Test
    public void CreateTest() {
        MyTimer timers = new MyTimer(true, 5, null);
        Thread timersThread = new Thread(timers);
        timersThread.start();
    }


    @Test
    public void RunTest() {
        MyTimer timers = new MyTimer(true, 20, null);
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
            e.printStackTrace();
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

    @Test
    public void getTimeAsStringTest() {
        MyTimer timers = new MyTimer(true, 20, null);
        Thread timersThread = new Thread(timers);
        timersThread.start();

        timers.playGold();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        synchronized (timersThread) {
            String s2 = timers.getCurrentTimeGold().toString();
            System.out.println(s2);
            assert s2.equals("00:00:05") || s2.equals("00:00:04");

        }
    }

}
