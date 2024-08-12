package io.github.kamarias.dbf.event;

import org.springframework.context.ApplicationEvent;

public class DbfOffLeaderEvent extends ApplicationEvent {


    public DbfOffLeaderEvent(Object source) {
        super(source);
    }


}
