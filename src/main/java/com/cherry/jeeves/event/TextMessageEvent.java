package com.cherry.jeeves.event;

import com.cherry.jeeves.domain.shared.Message;

/**
 * 聊天文本消息事件
 *
 * @author tangjialin on 2018-08-08.
 */
public class TextMessageEvent extends MessageEvent {
    public TextMessageEvent(Object source, Message message) {
        super(source, message);
    }
}
