package com.minispring.context;

import com.minispring.beans.factory.exception.BeansException;

public interface ApplicationContextAware {
    void setApplicationContext(ApplicationContext applicationContext) throws BeansException;
}
