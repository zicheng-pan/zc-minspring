package com.minispring;

import com.minispring.beans.BeanDefinition;
import com.minispring.beans.BeanFactory;
import com.minispring.beans.SimpleBeanFactory;
import com.minispring.beans.XmlBeanDefinitionReader;
import com.minispring.exception.BeansException;
import com.minispring.resource.ClassPathXmlResource;
import com.minispring.resource.Resource;

public class ClassPathXmlApplicationContext implements BeanFactory {

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
}
