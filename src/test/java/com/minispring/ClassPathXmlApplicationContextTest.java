package com.minispring;

import com.minispring.testbean.*;
import org.junit.Test;

public class ClassPathXmlApplicationContextTest {

    @Test
    public void testgetobj() {
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("beans.xml");
        TestObj testobj = (TestObj) classPathXmlApplicationContext.getBean("testobj");
        testobj.sayHello();
    }

    @Test
    public void testgetobjandProperty() {
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("beans.xml");
        TestObjImplForProperty testobj = (TestObjImplForProperty) classPathXmlApplicationContext.getBean("testobj_set_by_property");
        testobj.say();
    }

    @Test
    public void testgetobjbyConstructor() {
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("beans.xml");
        TestObjImplForConstructor testobj = (TestObjImplForConstructor) classPathXmlApplicationContext.getBean("testobj_set_by_constructor");
        System.out.println(testobj);
    }

    @Test
    public void testbeanReference() {
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("beans.xml");
        ReferenceAClass testobj = (ReferenceAClass) classPathXmlApplicationContext.getBean("aclass");
        System.out.println(testobj.getId() + " " + testobj.getReferenceBClass().getId() + " " + testobj.getReferenceBClass().getReferenceCClass().getId() + " ");
    }
}