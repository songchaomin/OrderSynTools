package com.kuka.event;

import org.springframework.context.ApplicationEvent;


public class OrderSynEvent extends ApplicationEvent {
    public OrderSynEvent(Object source) {
        super(source);
    }
}
