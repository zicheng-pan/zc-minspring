package com.minispring.event;

public interface ApplicationEventPublisher {
    void publishEvent(ApplicationEvent event);

    void addApplicationListener(ApplicationListener listener);
}
