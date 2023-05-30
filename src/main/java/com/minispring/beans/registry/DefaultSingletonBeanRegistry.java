package com.minispring.beans.registry;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultSingletonBeanRegistry implements SinglentonBeanRegistry {

    protected List<String> beanNames = new ArrayList<>();

    protected final Map<String, Object> singletonBeans = new ConcurrentHashMap<>();

    @Override
    public void registerSinglenton(String beanName, Object singletonObject) {
        synchronized (this.singletonBeans) {
            this.singletonBeans.put(beanName, singletonObject);
            this.beanNames.add(beanName);
        }
    }

    @Override
    public Object getSinglenton(String beanName) {
        return this.singletonBeans.getOrDefault(beanName, null);
    }

    @Override
    public boolean containsSingleton(String beanName) {
        return this.beanNames.contains(beanName);
    }

    @Override
    public String[] getSinglentonNames() {
        return (String[]) this.beanNames.toArray();
    }

    protected void removeSingleton(String beanName) {
        synchronized (this.singletonBeans) {
            this.beanNames.remove(beanName);
            this.singletonBeans.remove(beanName);
        }
    }
}
