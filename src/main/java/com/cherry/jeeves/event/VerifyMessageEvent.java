package com.cherry.jeeves.event;

import com.cherry.jeeves.domain.shared.Message;

/**
 * 好友邀请消息事件
 *
 * @author tangjialin on 2018-08-08.
 */
public class VerifyMessageEvent extends MessageEvent {

    public VerifyMessageEvent(Object source, Message message) {
        super(source, message);
    }

}
