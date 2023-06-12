package com.minispring;

import com.minispring.beans.DefaultListableBeanFactory;
import com.minispring.beans.factory.ClassPathXmlApplicationContext;
import com.minispring.testbean.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClassPathXmlApplicationContextTest {

    @Test
    public void testgetobj() {
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("beans.xml", true);
        TestObj testobj = (TestObj) classPathXmlApplicationContext.getBean("testobj");
        testobj.sayHello();
    }

    @Test
    public void testgetobjandProperty() {
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("beans.xml", true);
        TestObjImplForProperty testobj = (TestObjImplForProperty) classPathXmlApplicationContext.getBean("testobj_set_by_property");
        testobj.say();
    }

    @Test
    public void testgetobjbyConstructor() {
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("beans.xml", true);
        TestObjImplForConstructor testobj = (TestObjImplForConstructor) classPathXmlApplicationContext.getBean("testobj_set_by_constructor");
        System.out.println(testobj);
    }

    @Test
    public void testbeanReference() {
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("beans.xml", true);
        ReferenceAClass testobj = (ReferenceAClass) classPathXmlApplicationContext.getBean("aclass");
        System.out.println(testobj.getId() + " " + testobj.getReferenceBClass().getId() + " " + testobj.getReferenceBClass().getReferenceCClass().getId() + " ");
    }


    @Test
    public void testbeanAutowire() {
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("beans.xml", true);
        AutowireTestBean test = (AutowireTestBean) classPathXmlApplicationContext.getBean("testautowirebean");
        test.say();
    }

    @Test
    public void testDefaultListableBeanFactory_1() {
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("beans.xml", true);
        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) classPathXmlApplicationContext.beanFactory;
        int beanDefinitionCount = beanFactory.getBeanDefinitionCount();
        Assert.assertEquals(8, beanDefinitionCount);
    }

    @Test
    public void testDefaultListableBeanFactory_2() {
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("beans.xml", true);
        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) classPathXmlApplicationContext.beanFactory;

        String[] beanNamesForType = beanFactory.getBeanNamesForType(TestReference.class);
        List<String> list = new ArrayList<>();
        list.add("aclass");
        list.add("bclass");
        list.add("cclass");

        Assert.assertEquals(list.size(), beanNamesForType.length);

        Arrays.stream(beanNamesForType).map(it ->
                it
        ).forEach(it -> {
            Assert.assertTrue(list.contains(it));
        });
    }

    @Test
    public void testDefaultListableBeanFactory_3() {
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("beans.xml", true);
        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) classPathXmlApplicationContext.beanFactory;
        String[] cclasses = beanFactory.getDependenciesForBean("cclass");
        Assert.assertEquals(cclasses[0],"aclass");
    }
}