package com.minispring.beanfactory;

import com.minispring.beanregistry.DefaultSingletonBeanRegistry;
import com.minispring.exception.BeansException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SimpleBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory, BeanDefinitionRegistry {


    private Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();
    private List<String> beanDefinitionNames = new ArrayList<>();

    /**
     * @param beanName 对应 beanDefinition 对应 具体的bean object
     * @return
     * @throws BeansException
     */
    @Override
    public Object getBean(String beanName) throws BeansException {
        // Get bean if exists.
        Object singleton = this.getSinglenton(beanName);
        // If bean doesn't exist init it.
        if (singleton == null) {
            int index = beanNames.indexOf(beanName);
            if (index != -1) {
                // Init bean
                BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
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
        this.beanNames.add(beanDefinition.getId());
        this.beanDefinitionMap.put(beanDefinition.getId(), beanDefinition);
        this.beanDefinitionNames.add(beanDefinition.getId());
    }

    @Override
    public Boolean containsBean(String beanName) {
        return this.containsSingleton(beanName);
    }

    @Override
    public void registerBean(String beanName, Object obj) {
        this.registerSinglenton(beanName, obj);
    }

    @Override
    public Boolean isSingleton(String beanName) {
        return this.beanDefinitionMap.get(beanName).isSingleton();
    }

    @Override
    public Boolean isPrototype(String beanName) {
        return this.beanDefinitionMap.get(beanName).isProtoType();
    }

    @Override
    public Class<?> getType(String beanName) {
        return this.beanDefinitionMap.get(beanName).getBeanClass().getClass();
    }

    @Override
    public void registerBeanDefinition(String name, BeanDefinition beanDefinition) {
        this.beanDefinitionMap.put(name, beanDefinition);
        this.beanNames.add(name);
        this.beanDefinitionMap.put(name, beanDefinition);
        this.beanDefinitionNames.add(name);
        /**
         * lazy-init bean
         */

        if (!beanDefinition.isLazyInit()) {
            try {
                getBean(name);
            } catch (BeansException e) {
                throw new RuntimeException(e);
            }
        }

    }

    @Override
    public void removeBeanDefinition(String name) {
        this.beanDefinitionMap.remove(name);
    }

    @Override
    public BeanDefinition getBeanDefinition(String name) {
        return this.beanDefinitionMap.get(name);
    }

    @Override
    public boolean containsBeanDefinition(String name) {
        return this.beanDefinitionMap.containsKey(name);
    }
}
