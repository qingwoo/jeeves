package com.cherry.jeeves.service;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.regex.Pattern;

@ConfigurationProperties(prefix = "wechat.url")
public class WechatUrlProperties {
    public static final Pattern UUID_PATTERN = Pattern.compile("window\\.QRLogin\\.code = (\\d+); window\\.QRLogin\\.uuid = \"(\\S+?)\";");
    public static final Pattern CHECK_LOGIN_PATTERN = Pattern.compile("window\\.code=(\\d+)");
    public static final Pattern USER_AVATAR_PATTERN = Pattern.compile("window\\.userAvatar = '(.+)'");
    public static final Pattern REDIRECT_URL_PATTERN = Pattern.compile("window\\.redirect_uri=\"((\\S+)\\/cgi-bin\\S+)\";");
    public static final Pattern SYNC_CHECK_PATTERN = Pattern.compile("window\\.synccheck=\\{retcode:\"(\\d+)\",selector:\"(\\d+)\"\\}");
}
