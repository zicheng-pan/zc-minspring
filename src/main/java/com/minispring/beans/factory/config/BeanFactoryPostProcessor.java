package com.minispring.beans.factory.config;

import com.minispring.exception.BeansException;

public interface BeanFactoryPostProcessor {
    Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException;

    Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException;
}
