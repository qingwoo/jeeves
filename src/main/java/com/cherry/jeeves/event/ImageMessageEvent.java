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

    /**
     * @param source        事件发布源
     * @param message       消息体
     * @param thumbImageUrl 图片缩略图链接
     * @param fullImageUrl  图片完整图链接
     */
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
