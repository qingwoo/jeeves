package com.cherry.jeeves.controller;

import com.cherry.jeeves.domain.response.LoginResult;
import com.cherry.jeeves.domain.shared.Contact;
import com.cherry.jeeves.enums.LoginCode;
import com.cherry.jeeves.service.CacheService;
import com.cherry.jeeves.service.LoginService;
import com.cherry.jeeves.service.WechatHttpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Collection;
import java.util.Set;

/**
 * 机器人登陆管理
 *
 * @author tangjialin on 2018-03-04
 */
@Controller
@RequestMapping
public class BotController {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private CacheService cacheService;
    @Resource
    private LoginService loginService;
    @Resource
    private WechatHttpService wechatHttpService;

    @GetMapping("/jslogin")
    @ResponseBody
    public ResponseEntity<String> jslogin() {
        try {
            String uuid = wechatHttpService.getUUID();
            if (uuid != null) {
                return ResponseEntity.ok(uuid);
            }
            return ResponseEntity.unprocessableEntity().body("已有登录的机器人,请先退出");
        } catch (Exception e) {
            logger.error("登录异常", e);
            return ResponseEntity.unprocessableEntity().body("登录异常");
        }
    }

    @GetMapping("/login")
    @ResponseBody
    public ResponseEntity<LoginResult> login(String uuid) {
        LoginResult result = wechatHttpService.login(uuid);
        int code = result.getCode();
        if (LoginCode.SUCCESS.getCode() == code) {
            try {
                loginService.loginProcess(result);
            } catch (IOException e) {
                logger.error("登录处理异常", e);
            }
//            return ResponseEntity.status(code).build();
//        } else if (LoginCode.EXPIRED.code() == code) {
//            return ResponseEntity.status(code).body("登录超时，重新加载二维码");
//        } else if (LoginCode.AWAIT_CONFIRMATION.code() == code) {
//            return ResponseEntity.status(code).body("请在手机上确认登录");
//        } else if (LoginCode.AWAIT_SCANNING.code() == code) {
//            return ResponseEntity.status(code).body("使用手机微信扫码登录");
        }
        return ResponseEntity.status(result.getCode()).body(result);
    }


    @RequestMapping("/botloginOut")
    @ResponseBody
    public void botloginOut() {
        try {
            wechatHttpService.logout();
        } catch (Exception e) {
            logger.error("机器人退出登陆异常", e);
        }
    }

    @GetMapping("/localgetcontact")
    @ResponseBody
    public ResponseEntity<Collection<Contact>> localGetContact() {
        return ResponseEntity.ok(cacheService.getAllAccounts().values());
    }

    @GetMapping("/webwxgetcontact")
    @ResponseBody
    public ResponseEntity<Collection<Contact>> webwxgetcontact() {
        try {
            Set<Contact> contacts = wechatHttpService.getContact();
            cacheService.setContacts(contacts);
            return ResponseEntity.ok(contacts);
        } catch (Exception e) {
            logger.error("刷新机器人异常", e);
        }
        return ResponseEntity.unprocessableEntity().build();
    }
}
