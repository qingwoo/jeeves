package com.cherry.jeeves.utils;

import org.springframework.scheduling.concurrent.CustomizableThreadFactory;

/**
 * 微信机器人消息监听线程工具
 *
 * @author tangjialin on 2018-08-06.
 */
public class MessageThreadUtils {
    private static final CustomizableThreadFactory THREAD_FACTORY = new CustomizableThreadFactory("bot-message-");

    static {
        THREAD_FACTORY.setDaemon(true);
    }

    public static void execute(Runnable runnable) {
        THREAD_FACTORY.newThread(runnable).start();
    }

}
