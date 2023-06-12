package com.minispring.beans.factory.event;

import java.util.EventListener;

/**
 * 监听到ApplicationEvent事件
 */
public class ApplicationListener implements EventListener {

    void onApplicationEvent(ApplicationEvent event) {
        System.out.println("---" + event.toString());
    }
}
