package com.minispring.beans.factory;

import com.minispring.beans.factory.config.BeanDefinition;
import com.minispring.exception.BeansException;

public interface BeanFactory {

    Object getBean(String beanName) throws BeansException;

    void registerBeanDefinition(BeanDefinition beanDefinition);

    Boolean containsBean(String beanName);

    void registerBean(String beanName, Object obj);

    Boolean isSingleton(String beanName);

    Boolean isPrototype(String beanName);

    Class<?> getType(String beanName);

}
