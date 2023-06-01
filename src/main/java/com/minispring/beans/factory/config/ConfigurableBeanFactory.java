package com.minispring.beans.factory.config;


/**
 * 维护Bean之间的依赖关系以及支持Bean的postProcessor抽离出一个独立的特性
 */
public interface ConfigurableBeanFactory extends BeanFactory, SinglentonBeanRegistry {

    String SCOPE_SINGLETON = "singleton";
    String SCOPE_PROTOTYPE = "prototype";

    void addBeanPostProcessor(BeanFactoryPostProcessor beanFactoryPostProcessor);

    int getBeanPostProcessorCount();

    void registerDependentBean(String beanName, String dependentBeanName);

    String[] getDependentBeans(String beanName);

    String[] getDependenciesForBean(String beanName);
}
