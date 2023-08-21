package com.ssosnik.apprating.event;

import org.springframework.context.ApplicationEvent;

public class DailyCsvLoadingCompletedEvent extends ApplicationEvent {

    public DailyCsvLoadingCompletedEvent(Object source) {
        super(source);
    }
}
