package com.cherry.jeeves.enums;

/**
 * 信息类型
 *
 * @author tangjialin on 2018-08-11.
 */
public enum MessageType {
    TEXT(1, "普通文本消息(含系统表情和emoji)"),
    IMAGE(3, "图片消息"),
    VOICE(34, "语音消息"),
    ADD_FRIEND(37, "添加好友请求"),
    POSSIBLEFRIEND_MSG(40, "POSSIBLEFRIEND_MSG"),
    PERSON_CARD(42, "分享名片"),
    VIDEO(43, "视频消息"),
    EMOTICON(47, "动画表情"),
    LOCATION(48, "位置消息"),
    /** AppMsgType=33=>分享微信小程序 | AppMsgType=5=>分享链接 */
    SHARE(49, "分享消息"),
    VOIPMSG(50, "VOIPMSG"),
    CONTACT_INIT(51, "联系人初始化"),
    VOIPNOTIFY(52, "VOIPNOTIFY"),
    VOIPINVITE(53, "VOIPINVITE"),
    MICRO_VIDEO(62, "小视频"),
    SYSNOTICE(9999, "SYSNOTICE"),
    SYSTEM(10000, "系统消息"),
    REVOKE_MSG(10002, "撤回消息"),
    ;

    private final int code;
    private final String remark;

    MessageType(int code, String remark) {
        this.code = code;
        this.remark = remark;
    }

    public int code() {
        return code;
    }

    public String remark() {
        return remark;
    }
}
