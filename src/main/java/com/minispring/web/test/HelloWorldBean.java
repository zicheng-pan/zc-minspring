package com.minispring.web.test;

import com.minispring.web.RequestMapping;

public class HelloWorldBean {
    @RequestMapping("/helloworld")
    public String doGet() {
        return "hello,world! get";
    }
    @RequestMapping("/helloworld2")
    public String doPost() {
        return "hello world! post";
    }
}
