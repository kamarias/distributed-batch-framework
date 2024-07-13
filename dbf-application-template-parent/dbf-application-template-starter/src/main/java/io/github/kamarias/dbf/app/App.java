package io.github.kamarias.dbf.app;

import io.github.kamarias.dbf.annotation.EnableProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.HashMap;

/**
 * Hello world!
 */
@SpringBootApplication
@EnableProcessor
public class App {
    public static void main(String[] args) {

        ConfigurableApplicationContext context = SpringApplication.run(App.class, args);


        TestProcess testProcess1 = context.getBean("testProcess1",TestProcess.class);
        testProcess1.start(new HashMap<>());

        TestProcess12 testProcess12 = context.getBean("testProcess12",TestProcess12.class);
        testProcess1.start(new HashMap<>());

        TestProcess12 testProcess13 = context.getBean("testProcess12",TestProcess12.class);
        testProcess1.start(new HashMap<>());

    }
}
