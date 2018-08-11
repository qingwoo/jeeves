package com.cherry.jeeves.event;

import com.cherry.jeeves.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ChatRoomMembersChangedEventListener implements ApplicationListener<ChatRoomMembersChangedEvent> {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void onApplicationEvent(ChatRoomMembersChangedEvent event) {
        logger.info("ChatRoomMembersChangedEvent\n群:{}\n新加入的群成员:{}\n离开的群成员:{}",
                JsonUtils.toObject(event.getChatRoom()),
                JsonUtils.toObject(event.getMembersJoined()),
                JsonUtils.toObject(event.getMembersLeft()));
    }
}