package com.minispring.testbean;

import com.minispring.beans.factory.annotation.Autowired;

public class AutowireTestBean {

    @Autowired
    private TestObjImpl testobj;

    public void say() {
        testobj.sayHello();
    }

    public TestObjImpl getTestObj() {
        return testobj;
    }

    public void setTestObj(TestObjImpl testObj) {
        this.testobj = testObj;
    }
}
