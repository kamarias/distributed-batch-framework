package io.github.kamarias.dbf.event;

import org.springframework.context.ApplicationEvent;

public abstract class DbfEvent extends ApplicationEvent {


    public DbfEvent(Object source) {
        super(source);
    }

}
