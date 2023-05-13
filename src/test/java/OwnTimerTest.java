import cz.cvut.fel.pjv.model.OwnTimer;
import org.junit.jupiter.api.Test;

import static java.lang.Thread.sleep;

public class OwnTimerTest {

    @Test
    public void createTimerTest() {
        OwnTimer goldTimer = new OwnTimer(10, null);
        Thread gold = new Thread(goldTimer);

        gold.start();
        try {
            gold.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void createBothTimersTest() {
        OwnTimer timer = new OwnTimer(10, null);
        Thread gold = new Thread(timer),
                silver = new Thread(timer);

        gold.start();
        silver.start();
        try {
            gold.join();
            silver.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public synchronized void startAndPauseTest() {
        OwnTimer timer = new OwnTimer(10, null);
        Thread gold = new Thread(timer),
                silver = new Thread(timer);

        gold.start();
        try {
            sleep(2000);
            if (gold.isAlive()) {
                synchronized (gold) {
                    gold.wait();
                }
                silver.start();
                sleep(2000);
                synchronized (silver) {
                    silver.wait();
                }
                synchronized (gold) {
                    gold.notify();
                }
                wait(5000);
                synchronized (silver) {
                    silver.notify();
                }
            }

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        try {
            gold.join();
            silver.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
