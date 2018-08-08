package com.cherry.jeeves.domain.shared;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Message {
    @JsonProperty
    private String MsgId;
    @JsonProperty
    private String FromUserName;
    @JsonProperty
    private String ToUserName;
    @JsonProperty
    private int MsgType;
    @JsonProperty
    private String Content;
    @JsonProperty
    private long Status;
    @JsonProperty
    private long ImgStatus;
    @JsonProperty
    private long CreateTime;
    @JsonProperty
    private long VoiceLength;
    @JsonProperty
    private long PlayLength;
    @JsonProperty
    private String FileName;
    @JsonProperty
    private String FileSize;
    @JsonProperty
    private String MediaId;
    @JsonProperty
    private String Url;
    @JsonProperty
    private int AppMsgType;
    @JsonProperty
    private int StatusNotifyCode;
    @JsonProperty
    private String StatusNotifyUserName;
    @JsonProperty
    private RecommendInfo RecommendInfo;
    @JsonProperty
    private int ForwardFlag;
    @JsonProperty
    private AppInfo AppInfo;
    @JsonProperty
    private int HasProductId;
    @JsonProperty
    private String Ticket;
    @JsonProperty
    private int ImgHeight;
    @JsonProperty
    private int ImgWidth;
    @JsonProperty
    private int SubMsgType;
    @JsonProperty
    private long NewMsgId;
    @JsonProperty
    private String OriContent;

    public String getMsgId() {
        return MsgId;
    }

    public Message setMsgId(String msgId) {
        MsgId = msgId;
        return this;
    }

    public String getFromUserName() {
        return FromUserName;
    }

    public Message setFromUserName(String fromUserName) {
        FromUserName = fromUserName;
        return this;
    }

    public String getToUserName() {
        return ToUserName;
    }

    public Message setToUserName(String toUserName) {
        ToUserName = toUserName;
        return this;
    }

    public int getMsgType() {
        return MsgType;
    }

    public Message setMsgType(int msgType) {
        MsgType = msgType;
        return this;
    }

    public String getContent() {
        return Content;
    }

    public Message setContent(String content) {
        Content = content;
        return this;
    }

    public long getStatus() {
        return Status;
    }

    public Message setStatus(long status) {
        Status = status;
        return this;
    }

    public long getImgStatus() {
        return ImgStatus;
    }

    public Message setImgStatus(long imgStatus) {
        ImgStatus = imgStatus;
        return this;
    }

    public long getCreateTime() {
        return CreateTime;
    }

    public Message setCreateTime(long createTime) {
        CreateTime = createTime;
        return this;
    }

    public long getVoiceLength() {
        return VoiceLength;
    }

    public Message setVoiceLength(long voiceLength) {
        VoiceLength = voiceLength;
        return this;
    }

    public long getPlayLength() {
        return PlayLength;
    }

    public Message setPlayLength(long playLength) {
        PlayLength = playLength;
        return this;
    }

    public String getFileName() {
        return FileName;
    }

    public Message setFileName(String fileName) {
        FileName = fileName;
        return this;
    }

    public String getFileSize() {
        return FileSize;
    }

    public Message setFileSize(String fileSize) {
        FileSize = fileSize;
        return this;
    }

    public String getMediaId() {
        return MediaId;
    }

    public Message setMediaId(String mediaId) {
        MediaId = mediaId;
        return this;
    }

    public String getUrl() {
        return Url;
    }

    public Message setUrl(String url) {
        Url = url;
        return this;
    }

    public int getAppMsgType() {
        return AppMsgType;
    }

    public Message setAppMsgType(int appMsgType) {
        AppMsgType = appMsgType;
        return this;
    }

    public int getStatusNotifyCode() {
        return StatusNotifyCode;
    }

    public Message setStatusNotifyCode(int statusNotifyCode) {
        StatusNotifyCode = statusNotifyCode;
        return this;
    }

    public String getStatusNotifyUserName() {
        return StatusNotifyUserName;
    }

    public Message setStatusNotifyUserName(String statusNotifyUserName) {
        StatusNotifyUserName = statusNotifyUserName;
        return this;
    }

    public com.cherry.jeeves.domain.shared.RecommendInfo getRecommendInfo() {
        return RecommendInfo;
    }

    public Message setRecommendInfo(com.cherry.jeeves.domain.shared.RecommendInfo recommendInfo) {
        RecommendInfo = recommendInfo;
        return this;
    }

    public int getForwardFlag() {
        return ForwardFlag;
    }

    public Message setForwardFlag(int forwardFlag) {
        ForwardFlag = forwardFlag;
        return this;
    }

    public com.cherry.jeeves.domain.shared.AppInfo getAppInfo() {
        return AppInfo;
    }

    public Message setAppInfo(com.cherry.jeeves.domain.shared.AppInfo appInfo) {
        AppInfo = appInfo;
        return this;
    }

    public int getHasProductId() {
        return HasProductId;
    }

    public Message setHasProductId(int hasProductId) {
        HasProductId = hasProductId;
        return this;
    }

    public String getTicket() {
        return Ticket;
    }

    public Message setTicket(String ticket) {
        Ticket = ticket;
        return this;
    }

    public int getImgHeight() {
        return ImgHeight;
    }

    public Message setImgHeight(int imgHeight) {
        ImgHeight = imgHeight;
        return this;
    }

    public int getImgWidth() {
        return ImgWidth;
    }

    public Message setImgWidth(int imgWidth) {
        ImgWidth = imgWidth;
        return this;
    }

    public int getSubMsgType() {
        return SubMsgType;
    }

    public Message setSubMsgType(int subMsgType) {
        SubMsgType = subMsgType;
        return this;
    }

    public long getNewMsgId() {
        return NewMsgId;
    }

    public Message setNewMsgId(long newMsgId) {
        NewMsgId = newMsgId;
        return this;
    }

    public String getOriContent() {
        return OriContent;
    }

    public Message setOriContent(String oriContent) {
        OriContent = oriContent;
        return this;
    }
}
