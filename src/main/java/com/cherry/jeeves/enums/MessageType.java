package com.cherry.jeeves.enums;

/**
 * 信息类型
 *
 * @author tangjialin on 2018-08-11.
 */
public enum MessageType {
    TEXT(1, "文本消息"),
    IMAGE(3, "图片消息"),
    VOICE(34, "语音消息"),
    VERIFYMSG(37, "好友确认消息"),
    POSSIBLEFRIEND_MSG(40, "POSSIBLEFRIEND_MSG"),
    SHARECARD(42, "共享名片"),
    VIDEO(43, "视频消息"),
    EMOTICON(47, "动画表情"),
    LOCATION(48, "位置消息"),
    APP(49, "分享链接"),
    VOIPMSG(50, "VOIPMSG"),
    STATUSNOTIFY(51, "微信初始化消息"),
    VOIPNOTIFY(52, "VOIPNOTIFY"),
    VOIPINVITE(53, "VOIPINVITE"),
    MICROVIDEO(62, "小视频"),
    SYSNOTICE(9999, "SYSNOTICE"),
    SYS(10000, "系统消息"),
    RECALLED(10002, "撤回消息"),
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
