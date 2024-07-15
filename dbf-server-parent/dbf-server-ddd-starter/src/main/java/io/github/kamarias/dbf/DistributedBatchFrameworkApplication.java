package io.github.kamarias.dbf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class DistributedBatchFrameworkApplication {


    public static void main(String[] args) {
        ConfigurableApplicationContext context =
                SpringApplication.
                        run(DistributedBatchFrameworkApplication.class,
                                args);

    }


}
