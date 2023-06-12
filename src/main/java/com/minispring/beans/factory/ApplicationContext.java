package com.minispring.beans.factory;

import com.minispring.beans.factory.config.BeanFactoryPostProcessor;
import com.minispring.beans.factory.config.ConfigurableBeanFactory;
import com.minispring.beans.factory.config.ConfigurableListableBeanFactory;
import com.minispring.beans.factory.config.ListableBeanFactory;
import com.minispring.beans.factory.env.Environment;
import com.minispring.beans.factory.env.EnvironmentCapable;
import com.minispring.beans.factory.event.ApplicationEventPublisher;
import com.minispring.beans.factory.exception.BeansException;

/**
 * 先定义接口，然后用一个抽象类搭建框架，最后提供一个具体实现类进行默认实现 interface-abstract-class
 */
public interface ApplicationContext extends EnvironmentCapable, ListableBeanFactory, ConfigurableBeanFactory, ApplicationEventPublisher {

    String getApplicationName();

    long getStartupDate();

    ConfigurableListableBeanFactory getBeanFactory() throws
            IllegalStateException;

    void setEnvironment(Environment environment);

    Environment getEnvironment();

    void addBeanFactoryPostProcessor(BeanFactoryPostProcessor postProcessor);

    void refresh() throws BeansException, IllegalStateException;

    void close();

    boolean isActive();

}
