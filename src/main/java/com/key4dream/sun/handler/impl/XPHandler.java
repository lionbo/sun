package com.key4dream.sun.handler.impl;

import com.key4dream.sun.handler.IHandler;

public class XPHandler implements IHandler {

    public String process(String content) {
        return "http://m.anjuke.com/bj/xinfang/map/search/?gps=true&from=index_map_icon";
    }

}
