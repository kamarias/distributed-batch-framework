package io.github.kamarias.dbf.timer;

import io.netty.util.HashedWheelTimer;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class DbfHashedWheelTimer {


    private final HashedWheelTimer hashedWheelTimer = new HashedWheelTimer(100, TimeUnit.MILLISECONDS);


    public void start() {
        hashedWheelTimer.newTimeout(SecondTimerTask.getInstance(), 0, TimeUnit.SECONDS);
        hashedWheelTimer.newTimeout(MinutesTimerTask.getInstance(), 0, TimeUnit.HOURS);
        hashedWheelTimer.newTimeout(HoursTimerTask.getInstance(), 0, TimeUnit.MINUTES);
        hashedWheelTimer.start();
    }


    public void stop() {
        hashedWheelTimer.stop();
    }



    public void pendingTimeouts() {
        hashedWheelTimer.pendingTimeouts();
    }


}
