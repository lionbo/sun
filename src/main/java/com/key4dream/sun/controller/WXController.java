package com.key4dream.sun.controller;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.key4dream.sun.utils.Constants;

@Controller
@RequestMapping(value = "/wx")
public class WXController {

    private static Logger logger = LoggerFactory.getLogger(WXController.class);

    @RequestMapping(value = "/checkSignature", method = RequestMethod.GET)
    @ResponseBody
    public String get(@RequestParam("signature") String signature, @RequestParam("timestamp") String timestamp,
            @RequestParam("nonce") String nonce, @RequestParam("echostr") String echostr) {

        logger.info(signature + "," + timestamp + "," + nonce + "," + echostr);

        /**
         * 1. 将token、timestamp、nonce三个参数进行字典序排序
         * 2. 将三个参数字符串拼接成一个字符串进行sha1加密
         * 3. 开发者获得加密后的字符串可与signature对比，标识该请求来源于微信  
         */
        return echostr;
    }

    @RequestMapping(value = "/message/text", method = RequestMethod.POST)
    public String text(HttpServletRequest httpRequest) {
        try {
            InputStream is = httpRequest.getInputStream();
            try {
                byte[] body = new byte[httpRequest.getContentLength()];
                is.read(body, 0, httpRequest.getContentLength());
                String str = new String(body);
                logger.info(str);
            } finally {
                is.close();
            }
        } catch (IOException e) {
            logger.error("IOException", e);
            return Constants.REQUEST_FAIL;
        }
        return Constants.REQUEST_SUCCESS;
    }

}
