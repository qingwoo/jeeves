package com.cherry.jeeves.event;

import com.cherry.jeeves.domain.shared.Message;

/**
 * 群消息事件
 *
 * @author tangjialin on 2018-08-08.
 */
public class TextMessageEvent extends MessageEvent {
    public TextMessageEvent(Object source, Message message) {
        super(source, message);
    }
}
