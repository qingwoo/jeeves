package com.cherry.jeeves.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ImageMessageEventListener implements ApplicationListener<ImageMessageEvent> {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void onApplicationEvent(ImageMessageEvent event) {
        logger.info("ImageMessageEvent\n收到{}:{}的图片信息{}:{}:{}",
                event.getMessage().getRecommendInfo().getNickName(), event.getMessage().getFromUserName(),
                event.getMessage().getContent(), event.getThumbImageUrl(), event.getFullImageUrl());
    }
}