package com.minispring;

import com.minispring.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import com.minispring.beans.factory.annotation.AutowireCapableBeanFactory;
import com.minispring.beans.factory.BeanFactory;
import com.minispring.beans.factory.config.BeanDefinition;
import com.minispring.beans.factory.xml.XmlBeanDefinitionReader;
import com.minispring.beans.resource.ClassPathXmlResource;
import com.minispring.beans.resource.Resource;
import com.minispring.event.ApplicationEvent;
import com.minispring.event.ApplicationEventPublisher;
import com.minispring.exception.BeansException;

public class ClassPathXmlApplicationContext implements BeanFactory, ApplicationEventPublisher {

    BeanFactory beanFactory;

    public ClassPathXmlApplicationContext(String fileName, boolean isRefresh) {
        Resource resource = new ClassPathXmlResource(fileName);
        /*
            这个是使用xml来构建bean对象
            this.beanFactory = new SimpleBeanFactory();
         */

        /*
            这个AutowiredCapableBeanFactory类可以添加自定义processor,并且自定义processor可以实现@Autowired注解的注入
         */
        this.beanFactory = new AutowireCapableBeanFactory();


        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(this.beanFactory);
        beanDefinitionReader.loadBeanDefinitions(resource);
        if (isRefresh) {
            try {
                /*
                    这里refresh方法中添加了自己的AutowiredAnnotationBeanPostProcessor类，用来Autowire注解的解析
                    然后在注册进去
                 */
                this.refresh();
            } catch (BeansException e) {
                throw new RuntimeException(e);
            }
        }
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
    public void refresh() throws BeansException {
        registerBeanPostProcessors((AutowireCapableBeanFactory) this.beanFactory);
        onRefresh();
    }

    public void onRefresh() throws BeansException {
        this.beanFactory.refresh();
    }

    private void registerBeanPostProcessors(AutowireCapableBeanFactory beanFactory) {
        beanFactory.addBeanPostProcessor(new AutowiredAnnotationBeanPostProcessor((AutowireCapableBeanFactory) this.beanFactory));
    }

    @Override
    public void publishEvent(ApplicationEvent event) {

    }


}
