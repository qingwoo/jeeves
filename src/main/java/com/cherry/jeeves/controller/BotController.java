//package com.cherry.jeeves.controller;
//
//import com.cherry.jeeves.service.CacheService;
//import com.cherry.jeeves.service.SyncServie;
//import com.cherry.jeeves.service.WechatHttpService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import javax.annotation.Resource;
//import java.util.Collection;
//
///**
// * 机器人登陆管理
// *
// * @author tangjialin on 2018-03-04
// */
//@Controller
//@RequestMapping
//public class BotController {
//    private final Logger logger = LoggerFactory.getLogger(getClass());
//
//    @Resource
//    private CacheService cacheService;
//    @Resource
//    private SyncServie syncServie;
//    @Resource
//    private WechatHttpService wechatHttpService;
//
//    @GetMapping("/jslogin")
//    @ResponseBody
//    public ResponseEntity<String> jslogin() {
//        try {
//            String uuid = weChatBot.api().login();
//            if (uuid != null) {
////                String qrImageURI = weChatBot.api().getQrImageURI(uuid);
//                return ResponseEntity.ok(uuid);
//            }
//            return ResponseEntity.unprocessableEntity().body("已有登录的机器人,请先退出");
//        } catch (Exception e) {
//            logger.error("登录异常", e);
//            return ResponseEntity.unprocessableEntity().body("登录异常");
//        }
//    }
//
//    @GetMapping("/login")
//    @ResponseBody
//    public ResponseEntity<LoginResponse> login(String uuid, Integer tip) {
//        LoginResponse loginResponse = weChatBot.api().checkLogin(uuid, tip);
//        return ResponseEntity.status(loginResponse.getCode()).body(loginResponse);
////        if (StateCode.SUCCESS == code) {
////            return ResponseEntity.status(code).build();
////        } else if (StateCode.FAIL == code) {
////            return ResponseEntity.status(code).body("登录超时，重新加载二维码");
////        } else if (StateCode.WAIT_LOGIN == code) {
////            return ResponseEntity.status(code).body("请在手机上确认登录");
////        } else if (StateCode.WAIT_SCAN == code) {
////            return ResponseEntity.status(code).body("使用手机微信扫码登录");
////        }
////        return ResponseEntity.status(code).build();
//    }
//
//
//    @RequestMapping("/botloginOut")
//    @ResponseBody
//    public void botloginOut() {
//        try {
//            weChatBot.api().logout();
//        } catch (Exception e) {
//            logger.error("机器人退出登陆异常", e);
//        }
//    }
//
//    @GetMapping("/localgetcontact")
//    @ResponseBody
//    public ResponseEntity<Collection<Account>> localGetContact() {
//        return ResponseEntity.ok(weChatBot.api().getAllAccountList());
//    }
//
//    @GetMapping("/webwxgetcontact")
//    @ResponseBody
//    public ResponseEntity<Collection<Account>> webwxgetcontact() {
//        try {
//            Collection<Account> contactList = weChatBot.api().loadContact();
//            return ResponseEntity.ok(contactList);
//        } catch (Exception e) {
//            logger.error("刷新机器人异常", e);
//        }
//        return ResponseEntity.unprocessableEntity().build();
//    }
//}
