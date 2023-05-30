package com.minispring.resourceReader;

import com.minispring.beans.factory.BeanDefinition;
import com.minispring.beans.factory.BeanFactory;
import com.minispring.beans.resource.Resource;
import org.dom4j.Element;


public class XmlBeanDefinitionReader {

    private final BeanFactory beanFactory;

    public XmlBeanDefinitionReader(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public void loadBeanDefinitions(Resource resource) {
        Element element = (Element) resource.next();
        String beanID = element.attributeValue("id");
        String beanClassName = element.attributeValue("class");
        BeanDefinition beanDefinition = new BeanDefinition(beanID, beanClassName);
        this.beanFactory.registerBeanDefinition(beanDefinition);
    }
}
