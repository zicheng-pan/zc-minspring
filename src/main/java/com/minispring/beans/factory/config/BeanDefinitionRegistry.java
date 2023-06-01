package com.minispring.beans.factory.config;

import com.minispring.beans.factory.config.BeanDefinition;

public interface BeanDefinitionRegistry {

    void registerBeanDefinition(String name, BeanDefinition beanDefinition);

    void removeBeanDefinition(String name);

    BeanDefinition getBeanDefinition(String name);

    boolean containsBeanDefinition(String name);

}
