package com.cherry.jeeves.event;

import com.cherry.jeeves.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class SystemMessageEventListener implements ApplicationListener<SystemMessageEvent> {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void onApplicationEvent(SystemMessageEvent event) {
        logger.info("SystemMessageEvent\n收到{}:{}的信息{}:{}",
                event.getMessage().getRecommendInfo().getNickName(), event.getMessage().getFromUserName(),
                event.getMessage().getContent(),
                JsonUtils.toJson(event.getContact()));
    }

}