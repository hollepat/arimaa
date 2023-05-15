package cz.cvut.fel.pjv.utils;

import java.time.LocalTime;

public interface TimerEventListener {
    void tikTokGold(LocalTime currentTimeGold);
    void tikTokSilver( LocalTime currentTimeSilver);

}
