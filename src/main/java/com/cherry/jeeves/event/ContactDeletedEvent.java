package com.cherry.jeeves.event;

import com.cherry.jeeves.domain.shared.Contact;
import org.springframework.context.ApplicationEvent;

import java.util.Set;

/**
 * 联系人删除事件
 *
 * @author tangjialin on 2018-08-08.
 */
public class ContactDeletedEvent extends ApplicationEvent {
    private Set<Contact> contacts;

    /**
     * @param source   事件源
     * @param contacts 删除的联系人
     */
    public ContactDeletedEvent(Object source, Set<Contact> contacts) {
        super(source);
        this.contacts = contacts;
    }

    public Set<Contact> getContacts() {
        return contacts;
    }
}
