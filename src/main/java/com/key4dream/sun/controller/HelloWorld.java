package com.key4dream.sun.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.key4dream.sun.bo.SResponse;

@Controller
@RequestMapping(value = "/sayHello")
public class HelloWorld {

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public String get() {
        return "get hello world auto deploy test 2";
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public SResponse<String> post() {
        SResponse<String> s = new SResponse<String>();
        s.setData("post hello world");
        return s;
    }
}
