package com.cherry.jeeves.event;

import com.cherry.jeeves.domain.shared.Message;
import org.springframework.context.ApplicationEvent;

/**
 * 消息事件
 *
 * @author tangjialin on 2018-08-08.
 */
public class MessageEvent extends ApplicationEvent {
    private Message message;

    public MessageEvent(Object source, Message message) {
        super(source);
        this.message = message;
    }

    @Override
    public Object getSource() {
        return super.getSource();
    }

    public Message getMessage() {
        return message;
    }
}
