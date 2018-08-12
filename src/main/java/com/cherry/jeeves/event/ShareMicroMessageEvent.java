package com.cherry.jeeves.event;

import com.cherry.jeeves.domain.shared.Message;

/**
 * 分享小程序消息事件
 *
 * @author tangjialin on 2018-08-12.
 */
public class ShareMicroMessageEvent extends MessageEvent {
    public ShareMicroMessageEvent(Object source, Message message) {
        super(source, message);
    }
}
