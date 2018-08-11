package com.cherry.jeeves.event;

import com.cherry.jeeves.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ContactAddEventListener implements ApplicationListener<ContactAddEvent> {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void onApplicationEvent(ContactAddEvent event) {
        logger.info("ContactAddEvent\n新的好友/新增的群:{}",
                JsonUtils.toObject(event.getContacts()));
    }
}