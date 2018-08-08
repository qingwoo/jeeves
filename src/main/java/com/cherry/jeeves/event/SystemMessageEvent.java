package com.cherry.jeeves.event;

import com.cherry.jeeves.domain.shared.Contact;
import com.cherry.jeeves.domain.shared.Message;

/**
 * 系统消息事件
 *
 * @author tangjialin on 2018-08-08.
 */
public class SystemMessageEvent extends MessageEvent {
    private Contact contact;

    public SystemMessageEvent(Object source, Message message, Contact contact) {
        super(source, message);
        this.contact = contact;
    }

    public Contact getContact() {
        return contact;
    }
}
