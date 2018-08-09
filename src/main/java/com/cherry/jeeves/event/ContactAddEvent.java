package com.cherry.jeeves.event;

import com.cherry.jeeves.domain.shared.Contact;
import org.springframework.context.ApplicationEvent;

import java.util.Set;

/**
 * 新增好友事件
 *
 * @author tangjialin on 2018-08-08.
 */
public class ContactAddEvent extends ApplicationEvent {
    private Set<Contact> contacts;

    /**
     * @param source   事件源
     * @param contacts 新的好友/新增的群
     */
    public ContactAddEvent(Object source, Set<Contact> contacts) {
        super(source);
        this.contacts = contacts;
    }

    public Set<Contact> getContacts() {
        return contacts;
    }
}
