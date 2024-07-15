package io.github.kamarias.dbf.app;

import io.github.kamarias.dbf.process.Command;
import io.github.kamarias.dbf.annotation.EnableProcessor;
import io.github.kamarias.dbf.server.DbfServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;
import java.util.HashMap;

/**
 * Hello world!
 */
@SpringBootApplication
@EnableProcessor
public class App {
    public static void main(String[] args) throws IOException {

        ConfigurableApplicationContext context = SpringApplication.run(App.class, args);


        Command testProcess1 = context.getBean("testProcess1",Command.class);
        testProcess1.start(new HashMap<>());

        TestProcess12 testProcess12 = context.getBean("testProcess12",TestProcess12.class);
        testProcess1.start(new HashMap<>());

        TestProcess12 testProcess13 = context.getBean("testProcess12",TestProcess12.class);
        testProcess1.start(new HashMap<>());

        DbfServer dbfServer = new DbfServer();
        dbfServer.start("127.0.0.1",9091);

        System.in.read();
    }
}
