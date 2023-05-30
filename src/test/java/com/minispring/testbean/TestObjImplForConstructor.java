package com.minispring.testbean;

public class TestObjImplForConstructor {

    private String name;
    private int age;

    private String property1;
    private String property2;

    public TestObjImplForConstructor(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getProperty1() {
        return property1;
    }

    public void setProperty1(String property1) {
        this.property1 = property1;
    }

    public String getProperty2() {
        return property2;
    }

    public void setProperty2(String property2) {
        this.property2 = property2;
    }

    @Override
    public String toString() {
        return "{ \"name\": " + name + ", \"age\":" + age + "}";
    }
}
