package io.github.kamarias.dbf;

import io.github.kamarias.dbf.timer.DbfHashedWheelTimer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication(scanBasePackages = {"io.github.kamarias.dbf", "io.github.kamarias.dbf.gateway"})
public class DistributedBatchFrameworkApplication {


    public static void main(String[] args) {
        ConfigurableApplicationContext context =
                SpringApplication.
                        run(DistributedBatchFrameworkApplication.class,
                                args);
        DbfHashedWheelTimer dbfHashedWheelTimer = context.getBean(DbfHashedWheelTimer.class);
        dbfHashedWheelTimer.start();

    }


}
