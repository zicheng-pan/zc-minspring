package com.minispring.testbean;

public class TestObjImplForConstructor {

    private String name;
    private int age;

    public TestObjImplForConstructor(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "{ \"name\": " + name + ", \"age\":" + age + "}";
    }
}
