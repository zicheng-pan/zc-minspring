package com.minispring.beanfactory;

import com.minispring.beanregistry.DefaultSingletonBeanRegistry;
import com.minispring.exception.BeansException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimpleBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory {


    private List<BeanDefinition> beanDefinitions = new ArrayList<>();

    @Override
    public Object getBean(String beanName) throws BeansException {
        // Get bean if exists.
        Object singleton = this.getSinglenton(beanName);
        // If bean doesn't exist init it.
        if (singleton == null) {
            int index = beanNames.indexOf(beanName);
            if (index != -1) {
                // Init bean
                BeanDefinition beanDefinition = beanDefinitions.get(index);
                try {
                    singleton = Class.forName(beanDefinition.getClassName()).getDeclaredConstructor().newInstance();
                    this.registerSinglenton(beanName, singleton);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            } else {
                throw new BeansException("class not defined!");
            }
        }
        return singleton;
    }

    @Override
    public void registerBeanDefinition(BeanDefinition beanDefinition) {
        this.beanDefinitions.add(beanDefinition);
        this.beanNames.add(beanDefinition.getId());
    }

    @Override
    public Boolean containsBean(String beanName) {
        return this.containsSingleton(beanName);
    }

    @Override
    public void registerBean(String beanName, Object obj) {
        this.registerSinglenton(beanName, obj);
    }
}
