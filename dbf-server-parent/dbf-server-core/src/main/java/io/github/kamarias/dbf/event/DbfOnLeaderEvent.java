package io.github.kamarias.dbf.event;

import org.springframework.context.ApplicationEvent;


public class DbfOnLeaderEvent extends ApplicationEvent {


    public DbfOnLeaderEvent(Object source) {
        super(source);
    }

}
