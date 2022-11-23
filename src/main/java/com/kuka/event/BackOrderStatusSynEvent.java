package com.kuka.event;

import org.springframework.context.ApplicationEvent;


public class BackOrderStatusSynEvent extends ApplicationEvent {
    public BackOrderStatusSynEvent(Object source) {
        super(source);
    }
}
