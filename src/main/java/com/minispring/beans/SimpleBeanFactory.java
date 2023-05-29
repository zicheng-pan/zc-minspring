package com.minispring.beans;

import com.minispring.exception.BeansException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimpleBeanFactory implements BeanFactory {


    private List<BeanDefinition> beanDefinitions = new ArrayList<>();

    /*
        For index bean class if exists conveniently.
     */
    private List<String> beanNames = new ArrayList<>();
    private Map<String, Object> singletons = new HashMap<>();


    @Override
    public Object getBean(String beanName) throws BeansException {
        // Get bean if exists.
        Object singleton = singletons.getOrDefault(beanName, null);
        // If bean doesn't exist init it.
        if (singleton == null) {
            int index = beanNames.indexOf(beanName);
            if (index != -1) {
                // Init bean
                BeanDefinition beanDefinition = beanDefinitions.get(index);
                try {
                    singleton = Class.forName(beanDefinition.getClassName()).getDeclaredConstructor().newInstance();
                    singletons.put(beanDefinition.getId(), singleton);
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
}
