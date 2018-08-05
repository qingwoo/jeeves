package com.cherry.jeeves.domain.response;

public class LoginResult {
    private int code;
    private String redirectUrl;
    private String hostUrl;
    private String userAvatar;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public String getHostUrl() {
        return hostUrl;
    }

    public void setHostUrl(String hostUrl) {
        this.hostUrl = hostUrl;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public LoginResult setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
        return this;
    }
}