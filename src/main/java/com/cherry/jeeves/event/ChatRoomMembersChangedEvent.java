package com.cherry.jeeves.event;

import com.cherry.jeeves.domain.shared.ChatRoomMember;
import com.cherry.jeeves.domain.shared.Contact;
import org.springframework.context.ApplicationEvent;

import java.util.Set;

/**
 * 群成员发生变化事件
 *
 * @author tangjialin on 2018-08-08.
 */
public class ChatRoomMembersChangedEvent extends ApplicationEvent {
    private Contact chatRoom;
    private Set<ChatRoomMember> membersJoined;
    private Set<ChatRoomMember> membersLeft;

    /**
     * @param source        事件源
     * @param chatRoom      群
     * @param membersJoined 新加入的群成员
     * @param membersLeft   离开的群成员
     */
    public ChatRoomMembersChangedEvent(Object source, Contact chatRoom, Set<ChatRoomMember> membersJoined, Set<ChatRoomMember> membersLeft) {
        super(source);
        this.chatRoom = chatRoom;
        this.membersJoined = membersJoined;
        this.membersLeft = membersLeft;
    }

    public Contact getChatRoom() {
        return chatRoom;
    }

    public Set<ChatRoomMember> getMembersJoined() {
        return membersJoined;
    }

    public Set<ChatRoomMember> getMembersLeft() {
        return membersLeft;
    }
}
