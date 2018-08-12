package com.cherry.jeeves.event;

import com.cherry.jeeves.domain.shared.LinkContent;
import com.cherry.jeeves.domain.shared.Message;

/**
 * 分享URL消息事件
 *
 * @author tangjialin on 2018-08-12.
 */
public class ShareLinkMessageEvent extends MessageEvent {
    private LinkContent linkContent;

    public ShareLinkMessageEvent(Object source, Message message, LinkContent linkContent) {
        super(source, message);
        this.linkContent = linkContent;
    }

    public LinkContent getLinkContent() {
        return linkContent;
    }
}
