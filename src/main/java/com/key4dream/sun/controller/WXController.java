package com.key4dream.sun.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.key4dream.sun.bo.SResponse;

@Controller
@RequestMapping(value = "/wx")
public class WXController {

    @RequestMapping(value = "/checkSignature", method = RequestMethod.GET)
    @ResponseBody
    public String get(@RequestParam("signature") String signature, @RequestParam("timestamp") String timestamp,
            @RequestParam("nonce") String nonce, @RequestParam("echostr") String echostr) {
        /**
         * 1. 将token、timestamp、nonce三个参数进行字典序排序
         * 2. 将三个参数字符串拼接成一个字符串进行sha1加密
         * 3. 开发者获得加密后的字符串可与signature对比，标识该请求来源于微信  
         */
        return echostr;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public SResponse<String> post() {
        SResponse<String> s = new SResponse<String>();
        s.setData("post hello world");
        return s;
    }

}
