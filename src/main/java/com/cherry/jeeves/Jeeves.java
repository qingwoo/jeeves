package com.cherry.jeeves;

import com.cherry.jeeves.service.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class Jeeves {
    @Resource
    private LoginService loginService;
    @Value("${wechat.url.login}")
    private String url;
    @Value("${jeeves.instance-id}")
    private String instanceId;

    private static final Logger logger = LoggerFactory.getLogger(LoginService.class);

    public void start() {
        logger.info("Jeeves starts");
        logger.info("Jeeves id = " + instanceId);
        System.setProperty("jsse.enableSNIExtension", "false");
        loginService.login();
    }
}