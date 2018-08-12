package com.cherry.jeeves.event;

import com.cherry.jeeves.domain.shared.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ImageMessageEventListener implements ApplicationListener<ImageMessageEvent> {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void onApplicationEvent(ImageMessageEvent event) {
        Message message = event.getMessage();
        logger.info("ImageMessageEvent\n收到{}:{}的图片信息:{}:{}:{}",
                message.getRecommendInfo().getNickName(), message.getFromUserName(),
                message.getContent(), event.getThumbImageUrl(), event.getFullImageUrl());
    }
}