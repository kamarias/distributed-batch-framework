package io.github.kamarias.dbf.timer;

import io.netty.util.Timeout;
import io.netty.util.TimerTask;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

public class SecondTimer implements TimerTask {

    private static List<Object> taskList = new CopyOnWriteArrayList<>();


    private volatile static SecondTimer instance;

    @Override
    public void run(Timeout timeout) throws Exception {

        // 处理业务类

        timeout.timer().newTimeout(this,1, TimeUnit.SECONDS);
    }


    public static SecondTimer getInstance() {
        if (instance == null) {
            synchronized (SecondTimer.class) {
                if (instance == null) {
                    instance = new SecondTimer();
                }
            }
        }
        return instance;
    }


}
