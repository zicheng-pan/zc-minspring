package com.minispring.event;

import java.util.ArrayList;
import java.util.List;

public class SimpleApplicationEventPublisher implements ApplicationEventPublisher {

    List<ApplicationListener> list = new ArrayList<>();

    @Override
    public void publishEvent(ApplicationEvent event) {
        for (ApplicationListener listener : list) {
            listener.onApplicationEvent(event);
        }
    }

    @Override
    public void addApplicationListener(ApplicationListener listener) {
        list.add(listener);
    }
}
