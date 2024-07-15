package io.github.kamarias.dbf.timer;

import io.netty.util.HashedWheelTimer;

import java.util.concurrent.TimeUnit;

public class DbfHashedWheelTimer {


    private final HashedWheelTimer hashedWheelTimer = new HashedWheelTimer(10, TimeUnit.MILLISECONDS);


    public void start() {
        hashedWheelTimer.newTimeout(SecondTimer.getInstance(), 0, TimeUnit.SECONDS);


        hashedWheelTimer.start();
    }



    public void stop() {
        hashedWheelTimer.stop();
    }



    public void pendingTimeouts() {
        hashedWheelTimer.pendingTimeouts();
    }

}
