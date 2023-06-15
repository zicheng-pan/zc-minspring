package com.minispring.web.test;

import com.minispring.web.Bean;
import com.minispring.web.RequestMapping;
import com.minispring.web.test.service.AServiceImpl;

public class HelloWorldController {

    @Bean
    AServiceImpl aservice;

    @RequestMapping("/helloworld")
    public String doGet() {
        aservice.sayHello();
        return "hello,world! get";
    }

    @RequestMapping("/helloworld2")
    public String doPost() {
        return "hello world! post";
    }
}
