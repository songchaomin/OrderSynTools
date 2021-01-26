package com.kuka.event;

import org.springframework.context.ApplicationEvent;


public class OrderStatusSynEvent extends ApplicationEvent {
    public OrderStatusSynEvent(Object source) {
        super(source);
    }
}
