package io.github.kamarias.dbf.app;


import io.github.kamarias.dbf.process.AbstractProcessCommand;
import io.github.kamarias.dbf.annotation.Processor;

@Processor("testProcess12")
public class TestProcess12 extends AbstractProcessCommand {

    private final TestProcess testProcess;


    public TestProcess12(TestProcess testProcess) {
        this.testProcess = testProcess;
    }


}
