package com.key4dream.sun.handler;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.key4dream.sun.handler.impl.CLHandler;
import com.key4dream.sun.handler.impl.XHHandler;
import com.key4dream.sun.handler.impl.XPHandler;
import com.key4dream.sun.utils.Constants;

public final class HandlerDirector {

    private static Map<String, IHandler> handlerDirector = new HashMap<String, IHandler>();

    static {
        handlerDirector.put(Constants.KEY_WORD_XH, new XHHandler());
        handlerDirector.put(Constants.KEY_WORD_WYCL, new CLHandler());
        handlerDirector.put(Constants.KEY_WORD_XP, new XPHandler());
    }

    public static IHandler getHandler(String content) {
        if (StringUtils.isEmpty(content)) {
            return handlerDirector.get(Constants.KEY_WORD_XH);
        }
        return handlerDirector.get(content.toLowerCase());
    }

}
