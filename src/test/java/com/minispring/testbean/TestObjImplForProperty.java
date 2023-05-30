package com.minispring.testbean;

public class TestObjImplForProperty {
    private String property1;

    public void setProperty1(String property1) {
        this.property1 = property1;
    }

    public void say() {
        System.out.println("hello, " + property1);
    }
}
