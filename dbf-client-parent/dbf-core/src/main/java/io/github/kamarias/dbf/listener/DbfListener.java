package io.github.kamarias.dbf.listener;

import io.github.kamarias.dbf.event.DbfEvent;
import org.springframework.context.ApplicationListener;

public interface DbfListener<E extends DbfEvent> extends ApplicationListener<E> {



}
