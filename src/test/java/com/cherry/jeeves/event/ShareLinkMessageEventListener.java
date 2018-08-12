package com.cherry.jeeves.event;

import com.cherry.jeeves.domain.shared.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ShareLinkMessageEventListener implements ApplicationListener<ShareLinkMessageEvent> {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void onApplicationEvent(ShareLinkMessageEvent event) {
        Message message = event.getMessage();
        logger.info("ShareLinkMessageEvent\n收到{}:{}分享的链接:{} {}",
                message.getRecommendInfo().getNickName(), message.getFromUserName(),
                message.getUrl(), event.getLinkContent().getAppmsg().getUrl());
    }

}