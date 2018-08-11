package com.cherry.jeeves.event;

import com.cherry.jeeves.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class FriendInvitationMessageEventListener implements ApplicationListener<FriendInvitationMessageEvent> {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void onApplicationEvent(FriendInvitationMessageEvent event) {
        logger.info("FriendInvitationMessageEvent\n邀请信息:{}",
                JsonUtils.toObject(event.getRecommendInfo()));
    }
}