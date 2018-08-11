package com.cherry.jeeves.event;

import com.cherry.jeeves.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ContactDeletedEventListener implements ApplicationListener<ContactDeletedEvent> {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void onApplicationEvent(ContactDeletedEvent event) {
        logger.info("ContactDeletedEvent\n删除的联系人:{}",
                JsonUtils.toObject(event.getContacts()));
    }

}