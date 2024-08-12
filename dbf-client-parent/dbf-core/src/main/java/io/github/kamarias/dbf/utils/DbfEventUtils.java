package io.github.kamarias.dbf.utils;


import io.github.kamarias.dbf.event.DbfEvent;
import io.github.kamarias.dbf.listener.DbfListener;
import io.github.kamarias.dbf.listener.ReportTaskListener;
import io.github.kamarias.dbf.listener.TimerReportTaskListener;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.core.ResolvableType;
import org.springframework.lang.Nullable;

import java.util.function.Predicate;

public class DbfEventUtils {

    private static final ApplicationEventMulticaster events = new SimpleApplicationEventMulticaster();

    static {
        DbfEventUtils.addListener(new ReportTaskListener());
        DbfEventUtils.addListener(new TimerReportTaskListener());
    }

    private DbfEventUtils() {

    }

    public static void publishEvent(DbfEvent event) {
        events.multicastEvent(event);
    }

    public static void publishEvent(DbfEvent event, @Nullable ResolvableType eventType) {
        events.multicastEvent(event, eventType);
    }

    public static void addListener(DbfListener<?> listener) {
        events.addApplicationListener(listener);
    }

    public static void addListenerBean(String listenerBeanName) {
        events.addApplicationListenerBean(listenerBeanName);
    }

    public static void removeListener(DbfListener<?> listener) {
        events.removeApplicationListener(listener);
    }

    public static void removeListenerBean(String listenerBeanName) {
        events.removeApplicationListenerBean(listenerBeanName);
    }

    public static void removeListeners(Predicate<ApplicationListener<?>> predicate) {
        events.removeApplicationListeners(predicate);
    }

    public static void removeListenerBeans(Predicate<String> predicate) {
        events.removeApplicationListenerBeans(predicate);
    }

    public static void removeAllListeners() {
        events.removeAllListeners();
    }

    public static void multicastEvent(DbfEvent event) {
        events.multicastEvent(event);
    }

    public static void multicastEvent(DbfEvent event, @Nullable ResolvableType eventType) {
        events.multicastEvent(event, eventType);
    }

}
