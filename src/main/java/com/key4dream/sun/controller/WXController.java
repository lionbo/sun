package com.key4dream.sun.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.key4dream.sun.bo.WXMsg;
import com.key4dream.sun.utils.Constants;

@Controller
@RequestMapping(value = "/wx")
public class WXController {

    private static Logger logger = LoggerFactory.getLogger(WXController.class);

    @RequestMapping(value = "/message", method = RequestMethod.GET)
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

    @RequestMapping(value = "/message", method = RequestMethod.POST)
    @ResponseBody
    public String text(HttpServletRequest httpRequest) {
        try {
            InputStream is = httpRequest.getInputStream();
            try {
                byte[] body = new byte[httpRequest.getContentLength()];
                is.read(body, 0, httpRequest.getContentLength());
                String str = new String(body);
                WXMsg wxMsg = this.extractWxMsg(str);
                WXMsg reMsg = new WXMsg();
                reMsg.setFromUserName(wxMsg.getToUserName());
                reMsg.setToUserName(wxMsg.getFromUserName());
                reMsg.setContent("你的OpenId是" + wxMsg.getFromUserName());
                logger.info(wxMsg.toString());
                return this.changeWxMsgToXML(reMsg);
            } catch (DocumentException e) {
                logger.error("DocumentException", e);
                return Constants.REQUEST_FAIL;
            } finally {
                is.close();
            }
        } catch (IOException e) {
            logger.error("IOException", e);
            return Constants.REQUEST_FAIL;
        }
    }

    private String changeWxMsgToXML(WXMsg reMsg) {
        Document document = DocumentHelper.createDocument();
        Element root = document.getRootElement();
        Element ToUserName = root.addElement("ToUserName");
        Element FromUserName = root.addElement("FromUserName");
        Element CreateTime = root.addElement("CreateTime");
        Element MsgType = root.addElement("MsgType");
        Element Content = root.addElement("Content");

        ToUserName.addCDATA(reMsg.getToUserName());
        FromUserName.addCDATA(reMsg.getFromUserName());
        CreateTime.setText(new Date().getTime() + "");
        MsgType.addCDATA("text");
        Content.addCDATA(reMsg.getContent());
        return document.asXML();
    }

    private WXMsg extractWxMsg(String text) throws DocumentException {
        Document document = DocumentHelper.parseText(text);
        WXMsg msg = new WXMsg();
        Element root = document.getRootElement();
        msg.setToUserName(root.elementTextTrim("ToUserName"));
        msg.setFromUserName(root.elementTextTrim("FromUserName"));
        msg.setMsgType(root.elementTextTrim("MsgType"));
        msg.setContent(root.elementTextTrim("Content"));
        msg.setCreateTime(Long.valueOf(root.elementTextTrim("CreateTime")));
        msg.setMsgId(Long.valueOf(root.elementTextTrim("MsgId")));
        return msg;

    }

}
