package com.cherry.jeeves.event;

import com.cherry.jeeves.domain.shared.Message;
import com.cherry.jeeves.utils.MessageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class TextMessageEventListener implements ApplicationListener<TextMessageEvent> {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void onApplicationEvent(TextMessageEvent event) {
        Message message = event.getMessage();
        logger.info("TextMessageEvent\n收到{}:{}的信息:{}",
                message.getRecommendInfo().getNickName(), message.getFromUserName(),
                MessageUtils.getChatRoomTextMessageContent(message.getContent()));
    }

}