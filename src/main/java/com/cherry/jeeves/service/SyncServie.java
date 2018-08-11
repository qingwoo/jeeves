package com.cherry.jeeves.service;

import com.cherry.jeeves.JeevesProperties;
import com.cherry.jeeves.domain.response.SyncCheckResponse;
import com.cherry.jeeves.domain.response.SyncResponse;
import com.cherry.jeeves.domain.response.VerifyUserResponse;
import com.cherry.jeeves.domain.shared.ChatRoomMember;
import com.cherry.jeeves.domain.shared.Contact;
import com.cherry.jeeves.domain.shared.Message;
import com.cherry.jeeves.domain.shared.RecommendInfo;
import com.cherry.jeeves.domain.shared.VerifyUser;
import com.cherry.jeeves.enums.MessageType;
import com.cherry.jeeves.enums.RetCode;
import com.cherry.jeeves.enums.Selector;
import com.cherry.jeeves.event.ChatRoomMembersChangedEvent;
import com.cherry.jeeves.event.ContactAddEvent;
import com.cherry.jeeves.event.FriendInvitationMessageEvent;
import com.cherry.jeeves.event.ImageMessageEvent;
import com.cherry.jeeves.event.MessageEvent;
import com.cherry.jeeves.event.TextMessageEvent;
import com.cherry.jeeves.exception.WechatException;
import com.cherry.jeeves.utils.WechatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class SyncServie {
    private static final Logger logger = LoggerFactory.getLogger(SyncServie.class);
    @Resource
    private CacheService cacheService;
    @Resource
    private WechatHttpServiceInternal wechatHttpServiceInternal;
    @Resource
    private ApplicationEventPublisher applicationEventPublisher;
    @Resource
    private JeevesProperties jeevesProperties;

    private final static String RED_PACKET_CONTENT = "收到红包，请在手机上查看";

    public void listen() throws IOException, URISyntaxException {
        SyncCheckResponse syncCheckResponse = wechatHttpServiceInternal.syncCheck(
                cacheService.getSyncUrl(),
                cacheService.getBaseRequest().getUin(),
                cacheService.getBaseRequest().getSid(),
                cacheService.getBaseRequest().getSkey(),
                cacheService.getSyncKey());
        if (syncCheckResponse == null) { return; }
        int retCode = syncCheckResponse.getRetcode();
        int selector = syncCheckResponse.getSelector();
        logger.info("[SYNCCHECK] retCode:{} selector:{}", retCode, selector);
        if (retCode == RetCode.NORMAL.getCode()) {
            // 有新消息
            if (selector == Selector.NEW_MESSAGE.getCode()) {
                onNewMessage();
            } else if (selector == Selector.ENTER_LEAVE_CHAT.getCode()) {
                sync();
            } else if (selector == Selector.CONTACT_UPDATED.getCode()) {
                sync();
            } else if (selector == Selector.UNKNOWN1.getCode()) {
                sync();
            } else if (selector == Selector.UNKNOWN6.getCode()) {
                sync();
            } else if (selector != Selector.NORMAL.getCode()) {
                throw new WechatException("syncCheckResponse ret = " + retCode);
            }
        } else {
            logger.info("logout retCode:{} selector:{}", retCode, selector);
            throw new WechatException("syncCheckResponse selector = " + selector);
        }
    }

    private SyncResponse sync() throws IOException {
        SyncResponse syncResponse = wechatHttpServiceInternal.sync(cacheService.getHostUrl(), cacheService.getSyncKey(), cacheService.getBaseRequest());
        WechatUtils.checkBaseResponse(syncResponse);
        cacheService.setSyncKey(syncResponse.getSyncKey());
        cacheService.setSyncCheckKey(syncResponse.getSyncCheckKey());
        // mod包含新增和修改
        if (syncResponse.getModContactCount() > 0) {
            onContactsModified(syncResponse.getModContactList());
        }
        // del->联系人移除
        if (syncResponse.getDelContactCount() > 0) {
            onContactsDeleted(syncResponse.getDelContactList());
        }
        return syncResponse;
    }

    /**
     * 接受好友邀请
     *
     * @param info 邀请信息
     * @throws IOException
     * @throws URISyntaxException
     */
    public void acceptFriendInvitation(RecommendInfo info) throws IOException, URISyntaxException {
        VerifyUser user = new VerifyUser();
        user.setValue(info.getUserName());
        user.setVerifyUserTicket(info.getTicket());
        VerifyUserResponse verifyUserResponse = wechatHttpServiceInternal.acceptFriend(
                cacheService.getHostUrl(),
                cacheService.getBaseRequest(),
                cacheService.getPassTicket(),
                new VerifyUser[]{user}
        );
        WechatUtils.checkBaseResponse(verifyUserResponse);
    }

    private void onNewMessage() throws IOException, URISyntaxException {
        SyncResponse syncResponse = sync();
        String userName = cacheService.getOwner().getUserName();
        for (Message message : syncResponse.getAddMsgList()) {
            String fromUserName = message.getFromUserName();
            // 自己发出的消息
            if (userName.equals(fromUserName)) { continue; }
            Contact contact = cacheService.getAllAccounts().get(fromUserName);
            if (contact != null) {
                message.getRecommendInfo()
                        .setAlias(contact.getAlias())
                        .setAttrStatus(contact.getAttrStatus())
                        .setCity(contact.getCity())
                        .setNickName(contact.getNickName())
                        .setProvince(contact.getProvince())
                        .setSex(contact.getSex())
                        .setSignature(contact.getSignature())
                        .setUserName(contact.getUserName())
                        .setVerifyFlag(contact.getVerifyFlag())
                ;
            }
            applicationEventPublisher.publishEvent(new MessageEvent(this, message));
            // 文本消息
            if (message.getMsgType() == MessageType.TEXT.code()) {
                cacheService.getContactNamesWithUnreadMessage().add(fromUserName);
                applicationEventPublisher.publishEvent(new TextMessageEvent(this, message));
//                // 个人
//                if (WechatUtils.isMessageFromIndividual(fromUserName)) {
//                }
//                // 群
//                else if (WechatUtils.isMessageFromChatRoom(fromUserName)) {
//                }
            }
            // 图片
            else if (message.getMsgType() == MessageType.IMAGE.code()) {
                cacheService.getContactNamesWithUnreadMessage().add(fromUserName);
                String fullImageUrl = String.format(jeevesProperties.getUrl().getGetMsgImg(), cacheService.getHostUrl(), message.getMsgId(), cacheService.getsKey());
                String thumbImageUrl = fullImageUrl + "&type=slave";
                applicationEventPublisher.publishEvent(new ImageMessageEvent(this, message, thumbImageUrl, fullImageUrl));
//                // 个人
//                if (WechatUtils.isMessageFromIndividual(fromUserName)) {
//                }
//                // 群
//                else if (WechatUtils.isMessageFromChatRoom(fromUserName)) {
//                }
            }
//            // 系统消息
//            else if (message.getMsgType() == MessageType.SYS.code()) {
//                // 红包
//                if (RED_PACKET_CONTENT.equals(message.getContent())) {
//                    logger.info("[*] you've received a red packet");
//                    if (contact != null) {
//                        applicationEventPublisher.publishEvent(new SystemMessageEvent(this, message, contact));
//                    }
//                }
//            }
            // 好友邀请
            else if (message.getMsgType() == MessageType.VERIFYMSG.code() && userName.equals(message.getToUserName())) {
                applicationEventPublisher.publishEvent(new FriendInvitationMessageEvent(this, message.getRecommendInfo()));
//                if (messageHandler.onReceivingFriendInvitation(message.getRecommendInfo())) {
//                    acceptFriendInvitation(message.getRecommendInfo());
//                    logger.info("[*] you've accepted the invitation");
//                } else {
//                    logger.info("[*] you've declined the invitation");
//                    //TODO decline invitation
//                }
            }
        }
    }

    private void onContactsModified(Set<Contact> contacts) {
        Set<Contact> individuals = new HashSet<>();
        Set<Contact> chatRooms = new HashSet<>();
        Set<Contact> mediaPlatforms = new HashSet<>();

        for (Contact contact : contacts) {
            if (WechatUtils.isIndividual(contact)) {
                individuals.add(contact);
            } else if (WechatUtils.isMediaPlatform(contact)) {
                mediaPlatforms.add(contact);
            } else if (WechatUtils.isChatRoom(contact)) {
                chatRooms.add(contact);
            }
        }
        Map<String, Contact> allAccounts = cacheService.getAllAccounts();
        // individual
        if (individuals.size() > 0) {
            Set<Contact> existingIndividuals = cacheService.getIndividuals();
            Set<Contact> newIndividuals = individuals.stream().filter(x -> !existingIndividuals.contains(x)).collect(Collectors.toSet());
            individuals.forEach(x -> {
                existingIndividuals.remove(x);
                existingIndividuals.add(x);
                allAccounts.put(x.getUserName(), x);
            });
            if (newIndividuals.size() > 0) {
                applicationEventPublisher.publishEvent(new ContactAddEvent(this, newIndividuals));
            }
        }
        // chatroom
        if (chatRooms.size() > 0) {
            Set<Contact> existingChatRooms = cacheService.getChatRooms();
            Set<Contact> newChatRooms = new HashSet<>();
            Set<Contact> modifiedChatRooms = new HashSet<>();
            for (Contact chatRoom : chatRooms) {
                if (existingChatRooms.contains(chatRoom)) {
                    modifiedChatRooms.add(chatRoom);
                } else {
                    newChatRooms.add(chatRoom);
                }
                allAccounts.put(chatRoom.getUserName(), chatRoom);
            }
            existingChatRooms.addAll(newChatRooms);
            if (newChatRooms.size() > 0) {
                applicationEventPublisher.publishEvent(new ContactAddEvent(this, newChatRooms));
            }
            for (Contact chatRoom : modifiedChatRooms) {
                Contact existingChatRoom = existingChatRooms.stream().filter(x -> x.getUserName().equals(chatRoom.getUserName())).findFirst().orElse(null);
                if (existingChatRoom == null) { continue; }
                existingChatRooms.remove(existingChatRoom);
                existingChatRooms.add(chatRoom);

                Set<ChatRoomMember> oldMembers = existingChatRoom.getMemberList();
                Set<ChatRoomMember> newMembers = chatRoom.getMemberList();
                Set<ChatRoomMember> joined = newMembers.stream().filter(x -> !oldMembers.contains(x)).collect(Collectors.toSet());
                Set<ChatRoomMember> left = oldMembers.stream().filter(x -> !newMembers.contains(x)).collect(Collectors.toSet());
                if (joined.size() > 0 || left.size() > 0) {
                    applicationEventPublisher.publishEvent(new ChatRoomMembersChangedEvent(this, chatRoom, joined, left));
                }
            }
        }
        if (mediaPlatforms.size() > 0) {
            // media platform
            Set<Contact> existingPlatforms = cacheService.getMediaPlatforms();
            Set<Contact> newMediaPlatforms = existingPlatforms.stream().filter(x -> !existingPlatforms.contains(x)).collect(Collectors.toSet());
            mediaPlatforms.forEach(x -> {
                existingPlatforms.remove(x);
                existingPlatforms.add(x);
                allAccounts.put(x.getUserName(), x);
            });
            if (newMediaPlatforms.size() > 0) {
                applicationEventPublisher.publishEvent(new ContactAddEvent(this, newMediaPlatforms));
            }
        }
    }

    private void onContactsDeleted(Set<Contact> contacts) {
//        Set<Contact> individuals = new HashSet<>();
//        Set<Contact> chatRooms = new HashSet<>();
//        Set<Contact> mediaPlatforms = new HashSet<>();
        for (Contact contact : contacts) {
            if (WechatUtils.isIndividual(contact)) {
//                individuals.add(contact);
                cacheService.getIndividuals().remove(contact);
            } else if (WechatUtils.isChatRoom(contact)) {
//                chatRooms.add(contact);
                cacheService.getChatRooms().remove(contact);
            } else if (WechatUtils.isMediaPlatform(contact)) {
//                mediaPlatforms.add(contact);
                cacheService.getMediaPlatforms().remove(contact);
            }
            cacheService.getAllAccounts().remove(contact.getUserName());
        }
        applicationEventPublisher.publishEvent(new ContactAddEvent(this, contacts));
    }
}
