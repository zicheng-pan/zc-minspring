package com.minispring;

import com.minispring.beans.factory.BeanDefinition;
import com.minispring.beans.factory.BeanFactory;
import com.minispring.beans.factory.SimpleBeanFactory;
import com.minispring.resourceReader.XmlBeanDefinitionReader;
import com.minispring.event.ApplicationEvent;
import com.minispring.event.ApplicationEventPublisher;
import com.minispring.exception.BeansException;
import com.minispring.beans.resource.ClassPathXmlResource;
import com.minispring.beans.resource.Resource;

public class ClassPathXmlApplicationContext implements BeanFactory, ApplicationEventPublisher {

    BeanFactory beanFactory;

    public ClassPathXmlApplicationContext(String fileName) {
        Resource resource = new ClassPathXmlResource(fileName);
        this.beanFactory = new SimpleBeanFactory();
        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(this.beanFactory);
        beanDefinitionReader.loadBeanDefinitions(resource);
    }

    @Override
    public Object getBean(String beanName) {
        try {
            return this.beanFactory.getBean(beanName);
        } catch (BeansException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void registerBeanDefinition(BeanDefinition beanDefinition) {
        this.beanFactory.registerBeanDefinition(beanDefinition);
    }

    @Override
    public Boolean containsBean(String beanName) {
        return this.beanFactory.containsBean(beanName);
    }

    @Override
    public void registerBean(String beanName, Object obj) {
        this.beanFactory.registerBean(beanName, obj);
    }

    @Override
    public Boolean isSingleton(String beanName) {
        return false;
    }

    @Override
    public Boolean isPrototype(String beanName) {
        return false;
    }

    @Override
    public Class<?> getType(String beanName) {
        return null;
    }

    @Override
    public void publishEvent(ApplicationEvent event) {

    }
}
