package com.cherry.jeeves;

import com.cherry.jeeves.service.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Jeeves {

    private LoginService loginService;
    private String instanceId;

    private static final Logger logger = LoggerFactory.getLogger(LoginService.class);

    public Jeeves(String instanceId, LoginService loginService) {
        this.instanceId = instanceId;
        this.loginService = loginService;
    }

    public void start() {
        logger.info("Jeeves starts");
        logger.info("Jeeves id = " + instanceId);
        System.setProperty("jsse.enableSNIExtension", "false");
        loginService.login();
    }
}