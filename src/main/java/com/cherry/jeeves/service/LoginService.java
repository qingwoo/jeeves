package com.cherry.jeeves.service;

import com.cherry.jeeves.JeevesProperties;
import com.cherry.jeeves.domain.request.component.BaseRequest;
import com.cherry.jeeves.domain.response.BatchGetContactResponse;
import com.cherry.jeeves.domain.response.InitResponse;
import com.cherry.jeeves.domain.response.LoginResult;
import com.cherry.jeeves.domain.response.StatusNotifyResponse;
import com.cherry.jeeves.domain.shared.ChatRoomDescription;
import com.cherry.jeeves.domain.shared.Token;
import com.cherry.jeeves.enums.LoginCode;
import com.cherry.jeeves.enums.StatusNotifyCode;
import com.cherry.jeeves.exception.WechatException;
import com.cherry.jeeves.exception.WechatQRExpiredException;
import com.cherry.jeeves.utils.MessageThreadUtils;
import com.cherry.jeeves.utils.QRCodeUtils;
import com.cherry.jeeves.utils.WechatUtils;
import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.IOException;

@Component
public class LoginService {
    private static final Logger logger = LoggerFactory.getLogger(LoginService.class);

    @Resource
    private CacheService cacheService;
    @Resource
    private SyncServie syncServie;
    @Resource
    private WechatHttpServiceInternal wechatHttpServiceInternal;
    @Resource
    private WechatHttpService wechatHttpService;
    @Resource
    private JeevesProperties jeevesProperties;

    private int qrRefreshTimes = 0;

    public void login() {
        try {
            //0 entry
            wechatHttpServiceInternal.open(qrRefreshTimes);
            logger.info("[0] entry completed");
            //1 uuid
            String uuid = wechatHttpServiceInternal.getUUID();
            cacheService.setUuid(uuid);
            logger.info("[1] uuid completed");
            //2 qr
            byte[] qrData = wechatHttpServiceInternal.getQR(uuid);
            ByteArrayInputStream stream = new ByteArrayInputStream(qrData);
            String qrUrl = QRCodeUtils.decode(stream);
            stream.close();
            String qr = QRCodeUtils.generateQR(qrUrl, 40, 40);
            logger.info("\r\n" + qr);
            logger.info("[2] qrcode completed");
            //3 statreport
            wechatHttpServiceInternal.statReport();
            logger.info("[3] statReport completed");
            //4 login
            LoginResult loginResponse;
            while (true) {
                loginResponse = wechatHttpServiceInternal.login(uuid);
                if (LoginCode.SUCCESS.getCode() == loginResponse.getCode()) {
                    loginProcess(loginResponse);
                    break;
                } else if (LoginCode.AWAIT_CONFIRMATION.getCode() == loginResponse.getCode()) {
                    logger.info("[*] login status = AWAIT_CONFIRMATION");
                } else if (LoginCode.AWAIT_SCANNING.getCode() == loginResponse.getCode()) {
                    logger.info("[*] login status = AWAIT_SCANNING");
                } else if (LoginCode.EXPIRED.getCode() == loginResponse.getCode()) {
                    logger.info("[*] login status = EXPIRED");
                    throw new WechatQRExpiredException();
                } else {
                    logger.info("[*] login status = " + loginResponse.getCode());
                }
            }
        } catch (IOException | NotFoundException | WriterException ex) {
            throw new WechatException(ex);
        } catch (WechatQRExpiredException ex) {
            if (jeevesProperties.isAutoReLoginWhenQrCodeExpired() && qrRefreshTimes <= jeevesProperties.getMaxQrRefreshTimes()) {
                login();
            } else {
                throw new WechatException(ex);
            }
        }
    }

    /**
     * 登录后处理
     *
     * @param result 登录结果
     * @throws IOException
     */
    public void loginProcess(LoginResult result) throws IOException {
        if (LoginCode.SUCCESS.getCode() != result.getCode()) {
            return;
        }
        String hostUrl = result.getHostUrl();
        if (hostUrl == null) {
            throw new WechatException("hostUrl can't be found");
        }
        if (result.getRedirectUrl() == null) {
            throw new WechatException("redirectUrl can't be found");
        }
        cacheService.setHostUrl(hostUrl);
        if ("https://wechat.com".equals(hostUrl)) {
            cacheService.setSyncUrl("https://webpush.web.wechat.com");
            cacheService.setFileUrl("https://file.web.wechat.com");
        } else {
            cacheService.setSyncUrl(hostUrl.replaceFirst("^https://", "https://webpush."));
            cacheService.setFileUrl(hostUrl.replaceFirst("^https://", "https://file."));
        }
        logger.info("[4] login completed");
        //5 redirect login
        Token token = wechatHttpServiceInternal.openNewLoginPage(result.getRedirectUrl());
        if (token.getRet() == 0) {
            cacheService.setPassTicket(token.getPass_ticket());
            cacheService.setsKey(token.getSkey());
            cacheService.setSid(token.getWxsid());
            cacheService.setUin(token.getWxuin());
            BaseRequest baseRequest = new BaseRequest();
            baseRequest.setUin(cacheService.getUin());
            baseRequest.setSid(cacheService.getSid());
            baseRequest.setSkey(cacheService.getsKey());
            cacheService.setBaseRequest(baseRequest);
        } else {
            throw new WechatException("token ret:" + token.getRet() + " message:" + token.getMessage());
        }
        logger.info("[5] redirect login completed");
        //6 redirect
        wechatHttpServiceInternal.redirect(cacheService.getHostUrl());
        logger.info("[6] redirect completed");
        //7 init
        InitResponse initResponse = wechatHttpServiceInternal.init(cacheService.getHostUrl(), cacheService.getBaseRequest());
        WechatUtils.checkBaseResponse(initResponse);
        cacheService.setSyncKey(initResponse.getSyncKey());
        cacheService.setOwner(initResponse.getUser());
        logger.info("[7] init completed");
        //8 status notify
        StatusNotifyResponse statusNotifyResponse = wechatHttpServiceInternal.statusNotify(cacheService.getHostUrl(),
                cacheService.getBaseRequest(),
                cacheService.getOwner().getUserName(), StatusNotifyCode.INITED.getCode());
        WechatUtils.checkBaseResponse(statusNotifyResponse);
        logger.info("[8] status notify completed");
        //9 get contact
        cacheService.setContacts(wechatHttpService.getContact());
        logger.info("[9] get contact completed");
        //10 batch get contact
        ChatRoomDescription[] chatRoomDescriptions = initResponse.getContactList().stream()
                .filter(x -> x != null && WechatUtils.isChatRoom(x))
                .map(x -> {
                    ChatRoomDescription description = new ChatRoomDescription();
                    description.setUserName(x.getUserName());
                    return description;
                })
                .toArray(ChatRoomDescription[]::new);
        if (chatRoomDescriptions.length > 0) {
            BatchGetContactResponse batchGetContactResponse = wechatHttpServiceInternal.batchGetContact(
                    cacheService.getHostUrl(),
                    cacheService.getBaseRequest(),
                    chatRoomDescriptions);
            WechatUtils.checkBaseResponse(batchGetContactResponse);
            logger.info("[*] batchGetContactResponse count:{}", batchGetContactResponse.getCount());
            cacheService.getChatRooms().addAll(batchGetContactResponse.getContactList());
        }
        logger.info("[10] batch get contact completed");
        cacheService.setAlive(true);
        logger.info("[*] login process completed");
        logger.info("[*] start listening");

        MessageThreadUtils.execute(() -> {
            syncServie.listen();
        });
    }
}
