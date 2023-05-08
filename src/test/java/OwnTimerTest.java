import cz.cvut.fel.pjv.model.OwnTimer;
import org.junit.jupiter.api.Test;

public class OwnTimerTest {

    @Test
    public void createTest() {
        OwnTimer ownTimer = new OwnTimer(10);
        Thread t = new Thread(ownTimer);
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
