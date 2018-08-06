package com.cherry.jeeves;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.regex.Pattern;

/**
 * 属性
 *
 * @author tangjialin on 2018-08-06.
 */
@Configuration
@ConfigurationProperties(prefix = "jeeves")
public class JeevesProperties {
    public static final Pattern UUID_PATTERN = Pattern.compile("window\\.QRLogin\\.code = (\\d+); window\\.QRLogin\\.uuid = \"(\\S+?)\";");
    public static final Pattern CHECK_LOGIN_PATTERN = Pattern.compile("window\\.code=(\\d+)");
    public static final Pattern USER_AVATAR_PATTERN = Pattern.compile("window\\.userAvatar = '(.+)'");
    public static final Pattern REDIRECT_URL_PATTERN = Pattern.compile("window\\.redirect_uri=\"((\\S+)\\/cgi-bin\\S+)\";");
    public static final Pattern SYNC_CHECK_PATTERN = Pattern.compile("window\\.synccheck=\\{retcode:\"(\\d+)\",selector:\"(\\d+)\"\\}");

    private String instanceId;

    private boolean autoReLoginWhenQrCodeExpired;
    private int maxQrRefreshTimes;

    private String userAgent;
    private URL url;

    /**
     * 请求URL
     *
     * @author tangjialin on 2018-08-06.
     */
    public static class URL {
        private String entry;
        private String uuid;
        private String qrcode;
        private String login;
        private String init;
        private String statReport;
        private String statusNotify;
        private String syncCheck;
        private String sync;
        private String getContact;
        private String sendMsg;
        private String uploadMedia;
        private String getMsgImg;
        private String getVoice;
        private String getVideo;
        private String pushLogin;
        private String logout;
        private String batchGetContact;
        private String opLog;
        private String verifyUser;
        private String getMedia;
        private String createChatRoom;
        private String deleteChatRoomMember;
        private String addChatRoomMember;

        public String getEntry() {
            return entry;
        }

        public URL setEntry(String entry) {
            this.entry = entry;
            return this;
        }

        public String getUuid() {
            return uuid;
        }

        public URL setUuid(String uuid) {
            this.uuid = uuid;
            return this;
        }

        public String getQrcode() {
            return qrcode;
        }

        public URL setQrcode(String qrcode) {
            this.qrcode = qrcode;
            return this;
        }

        public String getLogin() {
            return login;
        }

        public URL setLogin(String login) {
            this.login = login;
            return this;
        }

        public String getInit() {
            return init;
        }

        public URL setInit(String init) {
            this.init = init;
            return this;
        }

        public String getStatReport() {
            return statReport;
        }

        public URL setStatReport(String statReport) {
            this.statReport = statReport;
            return this;
        }

        public String getStatusNotify() {
            return statusNotify;
        }

        public URL setStatusNotify(String statusNotify) {
            this.statusNotify = statusNotify;
            return this;
        }

        public String getSyncCheck() {
            return syncCheck;
        }

        public URL setSyncCheck(String syncCheck) {
            this.syncCheck = syncCheck;
            return this;
        }

        public String getSync() {
            return sync;
        }

        public URL setSync(String sync) {
            this.sync = sync;
            return this;
        }

        public String getGetContact() {
            return getContact;
        }

        public URL setGetContact(String getContact) {
            this.getContact = getContact;
            return this;
        }

        public String getSendMsg() {
            return sendMsg;
        }

        public URL setSendMsg(String sendMsg) {
            this.sendMsg = sendMsg;
            return this;
        }

        public String getUploadMedia() {
            return uploadMedia;
        }

        public URL setUploadMedia(String uploadMedia) {
            this.uploadMedia = uploadMedia;
            return this;
        }

        public String getGetMsgImg() {
            return getMsgImg;
        }

        public URL setGetMsgImg(String getMsgImg) {
            this.getMsgImg = getMsgImg;
            return this;
        }

        public String getGetVoice() {
            return getVoice;
        }

        public URL setGetVoice(String getVoice) {
            this.getVoice = getVoice;
            return this;
        }

        public String getGetVideo() {
            return getVideo;
        }

        public URL setGetVideo(String getVideo) {
            this.getVideo = getVideo;
            return this;
        }

        public String getPushLogin() {
            return pushLogin;
        }

        public URL setPushLogin(String pushLogin) {
            this.pushLogin = pushLogin;
            return this;
        }

        public String getLogout() {
            return logout;
        }

        public URL setLogout(String logout) {
            this.logout = logout;
            return this;
        }

        public String getBatchGetContact() {
            return batchGetContact;
        }

        public URL setBatchGetContact(String batchGetContact) {
            this.batchGetContact = batchGetContact;
            return this;
        }

        public String getOpLog() {
            return opLog;
        }

        public URL setOpLog(String opLog) {
            this.opLog = opLog;
            return this;
        }

        public String getVerifyUser() {
            return verifyUser;
        }

        public URL setVerifyUser(String verifyUser) {
            this.verifyUser = verifyUser;
            return this;
        }

        public String getGetMedia() {
            return getMedia;
        }

        public URL setGetMedia(String getMedia) {
            this.getMedia = getMedia;
            return this;
        }

        public String getCreateChatRoom() {
            return createChatRoom;
        }

        public URL setCreateChatRoom(String createChatRoom) {
            this.createChatRoom = createChatRoom;
            return this;
        }

        public String getDeleteChatRoomMember() {
            return deleteChatRoomMember;
        }

        public URL setDeleteChatRoomMember(String deleteChatRoomMember) {
            this.deleteChatRoomMember = deleteChatRoomMember;
            return this;
        }

        public String getAddChatRoomMember() {
            return addChatRoomMember;
        }

        public URL setAddChatRoomMember(String addChatRoomMember) {
            this.addChatRoomMember = addChatRoomMember;
            return this;
        }
    }

    public String getInstanceId() {
        return instanceId;
    }

    public JeevesProperties setInstanceId(String instanceId) {
        this.instanceId = instanceId;
        return this;
    }

    public boolean isAutoReLoginWhenQrCodeExpired() {
        return autoReLoginWhenQrCodeExpired;
    }

    public JeevesProperties setAutoReLoginWhenQrCodeExpired(boolean autoReLoginWhenQrCodeExpired) {
        this.autoReLoginWhenQrCodeExpired = autoReLoginWhenQrCodeExpired;
        return this;
    }

    public int getMaxQrRefreshTimes() {
        return maxQrRefreshTimes;
    }

    public JeevesProperties setMaxQrRefreshTimes(int maxQrRefreshTimes) {
        this.maxQrRefreshTimes = maxQrRefreshTimes;
        return this;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public JeevesProperties setUserAgent(String userAgent) {
        this.userAgent = userAgent;
        return this;
    }

    public URL getUrl() {
        return url;
    }

    public JeevesProperties setUrl(URL url) {
        this.url = url;
        return this;
    }
}
