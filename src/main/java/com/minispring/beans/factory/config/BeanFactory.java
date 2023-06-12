package com.minispring.beans.factory.config;

import com.minispring.beans.factory.exception.BeansException;

public interface BeanFactory {

    Object getBean(String beanName) throws BeansException;

    void registerBeanDefinition(BeanDefinition beanDefinition);

    Boolean containsBean(String beanName);

    void registerBean(String beanName, Object obj);

    Boolean isSingleton(String beanName);

    Boolean isPrototype(String beanName);

    Class<?> getType(String beanName);

    void refresh() throws BeansException;

}
