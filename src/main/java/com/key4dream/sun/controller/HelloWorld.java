package com.key4dream.sun.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/")
public class HelloWorld {

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public String get() {
        return "get hello world auto deploy test 2";
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public String post() {
        return "post hello world";
    }
}
