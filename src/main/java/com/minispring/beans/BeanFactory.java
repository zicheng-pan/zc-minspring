package com.minispring.beans;

import com.minispring.exception.BeansException;

public interface BeanFactory {

    Object getBean(String beanName) throws BeansException;

    void registerBeanDefinition(BeanDefinition beanDefinition);
}
