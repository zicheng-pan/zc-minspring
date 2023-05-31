package com.minispring.beans.factory.config;

public interface SinglentonBeanRegistry {

    void registerSinglenton(String beanName, Object singletonObject);

    Object getSinglenton(String beanName);

    boolean containsSingleton(String beanName);

    String[] getSinglentonNames();
}
