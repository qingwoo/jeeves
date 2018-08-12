package com.cherry.jeeves.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 消息处理工具
 *
 * @author tangjialin on 2018-08-11.
 */
public class MessageUtils {
    private static final Pattern PATTERN = Pattern.compile("^(@([0-9]|[a-z])+):");
    private static final Pattern BR_PATTERN = Pattern.compile("<br/>");

    public static String getChatRoomTextMessageContent(String content) {
        if (content == null) {
            throw new IllegalArgumentException("content");
        }
        return BR_PATTERN.matcher(PATTERN.matcher(content).replaceAll("")).replaceAll("\r\n");
    }

    public static String getSenderOfChatRoomTextMessage(String content) {
        if (content == null) {
            throw new IllegalArgumentException("content");
        }
        Matcher matcher = PATTERN.matcher(content);
        return matcher.find() ? matcher.group(1) : null;
    }
}
