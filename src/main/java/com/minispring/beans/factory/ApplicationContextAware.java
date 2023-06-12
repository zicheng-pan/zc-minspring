package com.minispring.beans.factory;

import com.minispring.beans.factory.exception.BeansException;

public interface ApplicationContextAware {
    void setApplicationContext(ApplicationContext applicationContext) throws BeansException;
}
