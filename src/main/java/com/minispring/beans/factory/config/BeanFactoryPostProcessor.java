package com.minispring.beans.factory.config;

import com.minispring.beans.factory.exception.BeansException;

/**
 * 如果有新增加的注解，可以在这里做处理类，可以参照@AutowiredAnnotationBeanFactoryPostProcessor 这个Autowired注解类来实现
 */
public interface BeanFactoryPostProcessor {
    Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException;

    Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException;
}
