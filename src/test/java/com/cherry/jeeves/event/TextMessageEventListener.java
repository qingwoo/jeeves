package com.cherry.jeeves.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class TextMessageEventListener implements ApplicationListener<TextMessageEvent> {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void onApplicationEvent(TextMessageEvent event) {
        logger.info("TextMessageEvent\n收到{}:{}的信息{}",
                event.getMessage().getRecommendInfo().getNickName(), event.getMessage().getFromUserName(),
                event.getMessage().getContent());
    }

}