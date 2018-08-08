//package com.cherry.jeeves;
//
//import com.cherry.jeeves.domain.shared.ChatRoomMember;
//import com.cherry.jeeves.domain.shared.Contact;
//import com.cherry.jeeves.domain.shared.FriendInvitationContent;
//import com.cherry.jeeves.domain.shared.Message;
//import com.cherry.jeeves.domain.shared.RecommendInfo;
//import com.cherry.jeeves.service.MessageHandler;
//import com.cherry.jeeves.service.WechatHttpService;
//import com.cherry.jeeves.utils.MessageUtils;
//import com.cherry.jeeves.utils.XmlUtils;
//import org.apache.commons.text.StringEscapeUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import javax.annotation.Resource;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.util.Set;
//import java.util.stream.Collectors;
//
//public class MessageHandlerImpl implements MessageHandler {
//
//    private static final Logger logger = LoggerFactory.getLogger(MessageHandlerImpl.class);
//    @Resource
//    private WechatHttpService wechatHttpService;
//
//    public MessageHandlerImpl() {
//    }
//
//    @Override
//    public void onReceivingChatRoomTextMessage(Message message) {
//        logger.info("onReceivingChatRoomTextMessage\nfrom chatroom: {}({})\nto: {}\ncontent: {}",
//                message.getRecommendInfo().getNickName(), message.getFromUserName(), message.getToUserName(),
//                MessageUtils.getChatRoomTextMessageContent(message.getContent()));
//    }
//
//    @Override
//    public void onReceivingChatRoomImageMessage(Message message, String thumbImageUrl, String fullImageUrl) {
//        logger.info("onReceivingChatRoomImageMessage thumbImageUrl:{} fullImageUrl:{}", thumbImageUrl, fullImageUrl);
//    }
//
//    @Override
//    public void onReceivingPrivateTextMessage(Message message) throws IOException {
//        logger.info("onReceivingPrivateTextMessage\nfrom : {}({})\nto: {}\ncontent: {}",
//                message.getRecommendInfo().getNickName(), message.getFromUserName(), message.getToUserName(),
//                message.getContent());
////        将原文回复给对方
//        replyMessage(message);
//    }
//
//    @Override
//    public void onReceivingPrivateImageMessage(Message message, String thumbImageUrl, String fullImageUrl) throws IOException {
//        logger.info("onReceivingPrivateImageMessage thumbImageUrl:{} fullImageUrl:{}", thumbImageUrl, fullImageUrl);
////        将图片保存在本地
//        byte[] data = wechatHttpService.downloadImage(thumbImageUrl);
//        FileOutputStream fos = new FileOutputStream("thumb.jpg");
//        fos.write(data);
//        fos.close();
//    }
//
//    @Override
//    public boolean onReceivingFriendInvitation(RecommendInfo info) {
//        logger.info("onReceivingFriendInvitation recommendinfo content:{}", info.getContent());
////        默认接收所有的邀请
//        return true;
//    }
//
//    @Override
//    public void postAcceptFriendInvitation(Message message) throws IOException {
//        logger.info("postAcceptFriendInvitation");
////        将该用户的微信号设置成他的昵称
//        String content = StringEscapeUtils.unescapeXml(message.getContent());
//        FriendInvitationContent friendInvitationContent = XmlUtils.getXmlMapper().readValue(content, FriendInvitationContent.class);
//        wechatHttpService.setAlias(message.getRecommendInfo().getUserName(), friendInvitationContent.getFromusername());
//    }
//
//    @Override
//    public void onChatRoomMembersChanged(Contact chatRoom, Set<ChatRoomMember> membersJoined, Set<ChatRoomMember> membersLeft) {
//        logger.info("onChatRoomMembersChanged");
//        logger.info("chatRoom:" + chatRoom.getUserName());
//        if (membersJoined != null && membersJoined.size() > 0) {
//            logger.info("membersJoined:" + String.join(",", membersJoined.stream().map(ChatRoomMember::getNickName).collect(Collectors.toList())));
//        }
//        if (membersLeft != null && membersLeft.size() > 0) {
//            logger.info("membersLeft:" + String.join(",", membersLeft.stream().map(ChatRoomMember::getNickName).collect(Collectors.toList())));
//        }
//    }
//
//    @Override
//    public void onNewChatRoomsFound(Set<Contact> chatRooms) {
//        logger.info("onNewChatRoomsFound");
//        chatRooms.forEach(x -> logger.info(x.getUserName()));
//    }
//
//    @Override
//    public void onChatRoomsDeleted(Set<Contact> chatRooms) {
//        logger.info("onChatRoomsDeleted");
//        chatRooms.forEach(x -> logger.info(x.getUserName()));
//    }
//
//    @Override
//    public void onNewFriendsFound(Set<Contact> contacts) {
//        logger.info("onNewFriendsFound");
//        contacts.forEach(x -> {
//            logger.info(x.getUserName());
//            logger.info(x.getNickName());
//        });
//    }
//
//    @Override
//    public void onFriendsDeleted(Set<Contact> contacts) {
//        logger.info("onFriendsDeleted");
//        contacts.forEach(x -> {
//            logger.info(x.getUserName());
//            logger.info(x.getNickName());
//        });
//    }
//
//    @Override
//    public void onNewMediaPlatformsFound(Set<Contact> mps) {
//        logger.info("onNewMediaPlatformsFound");
//    }
//
//    @Override
//    public void onMediaPlatformsDeleted(Set<Contact> mps) {
//        logger.info("onMediaPlatformsDeleted");
//    }
//
//    @Override
//    public void onRedPacketReceived(Contact contact) {
//        logger.info("onRedPacketReceived");
//        if (contact != null) {
//            logger.info("the red packet is from " + contact.getNickName());
//        }
//    }
//
//    private void replyMessage(Message message) throws IOException {
//        wechatHttpService.sendText(message.getFromUserName(), message.getContent());
//    }
//}
