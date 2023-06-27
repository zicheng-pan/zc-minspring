package com.minispring.web.test;

import com.minispring.beans.factory.annotation.Autowired;
import com.minispring.web.RequestMapping;
import com.minispring.web.RequestMethod;
import com.minispring.web.test.service.AServiceImpl;

public class HelloWorldController {

    @Autowired
    AServiceImpl aservice;

    @RequestMapping("/helloworld")
    public String doRequest() {
        aservice.sayHello();
        return "hello,world! request";
    }

    @RequestMapping(value = "/helloworld2", method = RequestMethod.POST)
    public String doPost() {
        return "hello world! post";
    }

    @RequestMapping(value = "/helloworld3", method = RequestMethod.GET)
    public String doGet() {
        aservice.sayHello();
        return "hello,world! get";
    }
}
