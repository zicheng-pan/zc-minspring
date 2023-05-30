package com.minispring.beanfactory;

import com.minispring.exception.BeansException;

public interface BeanFactory {

    Object getBean(String beanName) throws BeansException;

    void registerBeanDefinition(BeanDefinition beanDefinition);

    Boolean containsBean(String beanName);

    void registerBean(String beanName, Object obj);
}
