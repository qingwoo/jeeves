package com.cherry.jeeves.service;

import com.cherry.jeeves.JeevesProperties;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;

public class JeevesPropertiesTest {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Test
    public void uuidPattern() {
    }

    @Test
    public void checkLoginPattern() {
    }

    @Test
    public void userAvatarPattern() {
        String body = "window.code=201;window.userAvatar = 'data:img/jpg;base64,/9j/';";
        Matcher matcher = JeevesProperties.USER_AVATAR_PATTERN.matcher(body);
        Assert.assertTrue(matcher.find());
        Assert.assertEquals(matcher.group(1), "data:img/jpg;base64,/9j/");
    }

    @Test
    public void redirectUrlPattern() {
        String body = "window.code=200;\n" +
                "window.redirect_uri=\"https://wx.qq.com/cgi-bin/mmwebwx-bin/webwxnewloginpage?ticket=AQdUt8afBAbxTVKdqefiuNVD@qrticket_0&uuid=QcOw2Q-kaQ==&lang=zh_CN&scan=1530424149\";";
        Matcher matcher = JeevesProperties.REDIRECT_URL_PATTERN.matcher(body);
        Assert.assertTrue(matcher.find());
        Assert.assertEquals(matcher.group(1), "https://wx.qq.com/cgi-bin/mmwebwx-bin/webwxnewloginpage?ticket=AQdUt8afBAbxTVKdqefiuNVD@qrticket_0&uuid=QcOw2Q-kaQ==&lang=zh_CN&scan=1530424149");
        Assert.assertEquals(matcher.group(2), "https://wx.qq.com");
    }

    @Test
    public void syncCheckPattern() {
        String body = "window.synccheck={retcode:\"0\",selector:\"0\"}";
        Matcher matcher = JeevesProperties.SYNC_CHECK_PATTERN.matcher(body);
        Assert.assertTrue(matcher.find());
        Assert.assertEquals(matcher.group(1), "0");
        Assert.assertEquals(matcher.group(2), "0");
    }

}