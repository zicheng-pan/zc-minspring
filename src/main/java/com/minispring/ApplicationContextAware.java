package com.minispring;

import com.minispring.exception.BeansException;

public interface ApplicationContextAware {
    void setApplicationContext(ApplicationContext applicationContext) throws BeansException;
}
