package com.key4dream.sun.handler.impl;

import java.util.Map;
import java.util.Map.Entry;

import com.key4dream.sun.handler.IHandler;
import com.key4dream.sun.utils.CacheMapNeverDel;
import com.key4dream.sun.utils.Constants;

public class CLHandler implements IHandler {

    public String process(String content) {
        Map<String, String> urlList = (Map<String, String>) CacheMapNeverDel.instance().get(Constants.KEY_WORD_WYCL);
        StringBuilder sb = new StringBuilder();
        if (urlList == null) {
            sb.append("url fetch failed");
        } else {
            for (Entry<String, String> entry : urlList.entrySet()) {
                sb.append(entry.getKey());
                sb.append("\n");
                sb.append(entry.getValue());
                sb.append("\n");
            }
        }

        return sb.toString();
    }

}
