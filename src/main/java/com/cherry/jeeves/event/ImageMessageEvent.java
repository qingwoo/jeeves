package com.cherry.jeeves.event;

import com.cherry.jeeves.domain.shared.Message;

/**
 * 图片消息事件
 *
 * @author tangjialin on 2018-08-08.
 */
public class ImageMessageEvent extends MessageEvent {
    private String thumbImageUrl;
    private String fullImageUrl;

    public ImageMessageEvent(Object source, Message message, String thumbImageUrl, String fullImageUrl) {
        super(source, message);
        this.thumbImageUrl = thumbImageUrl;
        this.fullImageUrl = fullImageUrl;
    }

    public String getThumbImageUrl() {
        return thumbImageUrl;
    }

    public String getFullImageUrl() {
        return fullImageUrl;
    }
}
