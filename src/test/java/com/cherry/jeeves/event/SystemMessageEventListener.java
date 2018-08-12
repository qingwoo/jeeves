package com.cherry.jeeves.event;

import com.cherry.jeeves.domain.shared.Message;
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
        Message message = event.getMessage();
        logger.info("SystemMessageEvent\n收到{}:{}的信息:{}:{}",
                message.getRecommendInfo().getNickName(), message.getFromUserName(),
                message.getContent(),
                JsonUtils.toJson(event.getContact()));
    }

}