package com.cherry.jeeves.service;

import com.cherry.jeeves.JeevesProperties;
import com.cherry.jeeves.domain.request.AddChatRoomMemberRequest;
import com.cherry.jeeves.domain.request.BatchGetContactRequest;
import com.cherry.jeeves.domain.request.CreateChatRoomRequest;
import com.cherry.jeeves.domain.request.DeleteChatRoomMemberRequest;
import com.cherry.jeeves.domain.request.InitRequest;
import com.cherry.jeeves.domain.request.OpLogRequest;
import com.cherry.jeeves.domain.request.SendMsgRequest;
import com.cherry.jeeves.domain.request.StatReportRequest;
import com.cherry.jeeves.domain.request.StatusNotifyRequest;
import com.cherry.jeeves.domain.request.SyncRequest;
import com.cherry.jeeves.domain.request.VerifyUserRequest;
import com.cherry.jeeves.domain.request.component.BaseRequest;
import com.cherry.jeeves.domain.response.AddChatRoomMemberResponse;
import com.cherry.jeeves.domain.response.BatchGetContactResponse;
import com.cherry.jeeves.domain.response.CreateChatRoomResponse;
import com.cherry.jeeves.domain.response.DeleteChatRoomMemberResponse;
import com.cherry.jeeves.domain.response.GetContactResponse;
import com.cherry.jeeves.domain.response.InitResponse;
import com.cherry.jeeves.domain.response.LoginResult;
import com.cherry.jeeves.domain.response.OpLogResponse;
import com.cherry.jeeves.domain.response.SendMsgResponse;
import com.cherry.jeeves.domain.response.StatusNotifyResponse;
import com.cherry.jeeves.domain.response.SyncCheckResponse;
import com.cherry.jeeves.domain.response.SyncResponse;
import com.cherry.jeeves.domain.response.VerifyUserResponse;
import com.cherry.jeeves.domain.shared.BaseMsg;
import com.cherry.jeeves.domain.shared.ChatRoomDescription;
import com.cherry.jeeves.domain.shared.ChatRoomMember;
import com.cherry.jeeves.domain.shared.StatReport;
import com.cherry.jeeves.domain.shared.SyncKey;
import com.cherry.jeeves.domain.shared.Token;
import com.cherry.jeeves.domain.shared.VerifyUser;
import com.cherry.jeeves.enums.AddScene;
import com.cherry.jeeves.enums.MessageType;
import com.cherry.jeeves.enums.OpLogCmdId;
import com.cherry.jeeves.enums.StatusNotifyCode;
import com.cherry.jeeves.enums.VerifyUserOPCode;
import com.cherry.jeeves.exception.WechatException;
import com.cherry.jeeves.utils.DeviceIdGenerator;
import com.cherry.jeeves.utils.HeaderUtils;
import com.cherry.jeeves.utils.RandomUtils;
import com.cherry.jeeves.utils.WechatUtils;
import com.cherry.jeeves.utils.rest.StatefullRestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.apache.http.client.CookieStore;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;

@Component
class WechatHttpServiceInternal {
    @Resource
    private JeevesProperties jeevesProperties;
    private final RestTemplate restTemplate;
    private final HttpHeaders postHeader = new HttpHeaders();
    private final HttpHeaders getHeader = new HttpHeaders();
    private final ObjectMapper jsonMapper = new ObjectMapper();
    private String originValue = null;
    private String refererValue = null;
    private String BROWSER_DEFAULT_ACCEPT_LANGUAGE = "en,zh-CN;q=0.8,zh;q=0.6,ja;q=0.4,zh-TW;q=0.2";
    private String BROWSER_DEFAULT_ACCEPT_ENCODING = "gzip, deflate, br";

    WechatHttpServiceInternal(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        postHeader.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        postHeader.setAccept(Arrays.asList(MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN, MediaType.ALL));
        postHeader.set(HttpHeaders.ACCEPT_LANGUAGE, BROWSER_DEFAULT_ACCEPT_LANGUAGE);
        postHeader.set(HttpHeaders.ACCEPT_ENCODING, BROWSER_DEFAULT_ACCEPT_ENCODING);

        getHeader.set(HttpHeaders.ACCEPT_LANGUAGE, BROWSER_DEFAULT_ACCEPT_LANGUAGE);
        getHeader.set(HttpHeaders.ACCEPT_ENCODING, BROWSER_DEFAULT_ACCEPT_ENCODING);
    }

    @PostConstruct
    public void postConstruct() {
        postHeader.set(HttpHeaders.USER_AGENT, jeevesProperties.getUserAgent());
        getHeader.set(HttpHeaders.USER_AGENT, jeevesProperties.getUserAgent());
    }

    void logout(String hostUrl, String skey) throws IOException {
        final String url = String.format(jeevesProperties.getUrl().getLogout(), hostUrl, escape(skey));
        restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(postHeader), Object.class);
    }

    /**
     * Open the entry page.
     *
     * @param retryTimes retry times of qr scan
     */
    void open(int retryTimes) {
        HttpHeaders customHeader = new HttpHeaders();
        customHeader.setPragma("no-cache");
        customHeader.setCacheControl("no-cache");
        customHeader.set("Upgrade-Insecure-Requests", "1");
        customHeader.set(HttpHeaders.ACCEPT, "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
        HeaderUtils.assign(customHeader, getHeader);
        String url = jeevesProperties.getUrl().getEntry();
        restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(customHeader), String.class);
        //manually insert two cookies into cookiestore, as they're supposed to be stored in browsers by javascript.
        CookieStore store = (CookieStore) ((StatefullRestTemplate) restTemplate).getHttpContext().getAttribute(HttpClientContext.COOKIE_STORE);
        Date maxDate = new Date(Long.MAX_VALUE);
        String domain = url.replaceAll("https://", "").replaceAll("/", "");
        Map<String, String> cookies = new HashMap<>(3);
        cookies.put("MM_WX_NOTIFY_STATE", "1");
        cookies.put("MM_WX_SOUND_STATE", "1");
        if (retryTimes > 0) {
            cookies.put("refreshTimes", String.valueOf(retryTimes));
        }
        appendAdditionalCookies(store, cookies, domain, "/", maxDate);
//        RestTemplateWithCookies template = ((RestTemplateWithCookies) restTemplate)
//                .addCookies("MM_WX_NOTIFY_STATE", "1")
//                .addCookies("MM_WX_SOUND_STATE", "1");
//        if (retryTimes > 0) {
//            template.addCookies("refreshTimes", String.valueOf(retryTimes));
//        }
        //It's now at entry page.
        this.originValue = url;
        this.refererValue = url.replaceAll("/$", "");
    }

    /**
     * Get UUID for this session
     *
     * @return UUID
     */
    String getUUID() {
        final String url = String.format(jeevesProperties.getUrl().getUuid(), System.currentTimeMillis());
        final String successCode = "200";
        HttpHeaders customHeader = new HttpHeaders();
        customHeader.setPragma("no-cache");
        customHeader.setCacheControl("no-cache");
        customHeader.setAccept(Arrays.asList(MediaType.ALL));
        customHeader.set(HttpHeaders.REFERER, jeevesProperties.getUrl().getEntry());
        HeaderUtils.assign(customHeader, getHeader);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(customHeader), String.class);
        String body = responseEntity.getBody();
        Matcher matcher = JeevesProperties.UUID_PATTERN.matcher(body);
        if (matcher.find()) {
            if (successCode.equals(matcher.group(1))) {
                return matcher.group(2);
            }
        }
        throw new WechatException("uuid can't be found");
    }

    /**
     * Get QR code for scanning
     *
     * @param uuid UUID
     * @return QR code in binary
     */
    byte[] getQR(String uuid) {
        final String url = jeevesProperties.getUrl().getQrcode() + "/" + uuid;
        HttpHeaders customHeader = new HttpHeaders();
        customHeader.set(HttpHeaders.ACCEPT, "image/webp,image/apng,image/*,*/*;q=0.8");
        customHeader.set(HttpHeaders.REFERER, jeevesProperties.getUrl().getEntry());
        HeaderUtils.assign(customHeader, getHeader);
        ResponseEntity<byte[]> responseEntity = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(customHeader), new ParameterizedTypeReference<byte[]>() {
        });
        return responseEntity.getBody();
    }

    /**
     * Get hostUrl, redirectUrl and userAvatar
     *
     * @param uuid
     * @return hostUrl and redirectUrl
     * @throws WechatException if the response doesn't contain code
     */
    LoginResult login(String uuid) throws WechatException {
        long time = System.currentTimeMillis();
        final String url = String.format(jeevesProperties.getUrl().getLogin(), uuid, RandomUtils.generateDateWithBitwiseNot(time), time);
        HttpHeaders customHeader = new HttpHeaders();
        customHeader.setAccept(Arrays.asList(MediaType.ALL));
        customHeader.set(HttpHeaders.REFERER, jeevesProperties.getUrl().getEntry());
        HeaderUtils.assign(customHeader, getHeader);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(customHeader), String.class);
        String body = responseEntity.getBody();
        Matcher matcher = JeevesProperties.CHECK_LOGIN_PATTERN.matcher(body);
        LoginResult response = new LoginResult();
        if (matcher.find()) {
            response.setCode(Integer.parseInt(matcher.group(1)));
        } else {
            throw new WechatException("code can't be found");
        }
        Matcher userAvatarMatcher = JeevesProperties.USER_AVATAR_PATTERN.matcher(body);
        if (userAvatarMatcher.find()) {
            response.setUserAvatar(userAvatarMatcher.group(1));
        }
        Matcher redirectUrlMatcher = JeevesProperties.REDIRECT_URL_PATTERN.matcher(body);
        if (redirectUrlMatcher.find()) {
            response.setRedirectUrl(redirectUrlMatcher.group(1));
            response.setHostUrl(redirectUrlMatcher.group(2));
        }
        return response;
    }

    /**
     * Get basic parameters for this session
     *
     * @param redirectUrl
     * @return session token
     * @throws IOException if the http response body can't be convert to {@link Token}
     */
    Token openNewLoginPage(String redirectUrl) throws IOException {
        HttpHeaders customHeader = new HttpHeaders();
        customHeader.set(HttpHeaders.ACCEPT, "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
        customHeader.set(HttpHeaders.REFERER, jeevesProperties.getUrl().getEntry());
        customHeader.set("Upgrade-Insecure-Requests", "1");
        HeaderUtils.assign(customHeader, getHeader);
        ResponseEntity<String> responseEntity = restTemplate.exchange(redirectUrl, HttpMethod.GET, new HttpEntity<>(customHeader), String.class);
        String xmlString = responseEntity.getBody();
        ObjectMapper xmlMapper = new XmlMapper();
        return xmlMapper.readValue(xmlString, Token.class);
    }

    /**
     * Redirect to main page of wechat
     *
     * @param hostUrl hostUrl
     */
    void redirect(String hostUrl) {
        final String url = hostUrl + "/";
        HttpHeaders customHeader = new HttpHeaders();
        customHeader.set(HttpHeaders.ACCEPT, "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
        customHeader.set(HttpHeaders.REFERER, jeevesProperties.getUrl().getEntry());
        customHeader.set("Upgrade-Insecure-Requests", "1");
        HeaderUtils.assign(customHeader, getHeader);
        restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(customHeader), String.class);
        //It's now at main page.
        this.originValue = hostUrl;
        this.refererValue = hostUrl + "/";
    }

    /**
     * Initialization
     *
     * @param hostUrl     hostUrl
     * @param baseRequest baseRequest
     * @return current user's information and contact information
     * @throws IOException if the http response body can't be convert to {@link InitResponse}
     */
    InitResponse init(String hostUrl, BaseRequest baseRequest) throws IOException {
        String url = String.format(jeevesProperties.getUrl().getInit(), hostUrl, RandomUtils.generateDateWithBitwiseNot());

//        ((RestTemplateWithCookies) restTemplate)
//                .addCookies("MM_WX_NOTIFY_STATE", "1")
//                .addCookies("MM_WX_SOUND_STATE", "1");
        CookieStore store = (CookieStore) ((StatefullRestTemplate) restTemplate).getHttpContext().getAttribute(HttpClientContext.COOKIE_STORE);
        Date maxDate = new Date(Long.MAX_VALUE);
        String domain = hostUrl.replaceAll("https://", "").replaceAll("/", "");
        Map<String, String> cookies = new HashMap<>(3);
        cookies.put("MM_WX_NOTIFY_STATE", "1");
        cookies.put("MM_WX_SOUND_STATE", "1");
        appendAdditionalCookies(store, cookies, domain, "/", maxDate);
        InitRequest request = new InitRequest();
        request.setBaseRequest(baseRequest);
        HttpHeaders customHeader = new HttpHeaders();
        customHeader.set(HttpHeaders.REFERER, hostUrl + "/");
        customHeader.setOrigin(hostUrl);
        HeaderUtils.assign(customHeader, postHeader);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(request, customHeader), String.class);
        return jsonMapper.readValue(WechatUtils.textDecode(responseEntity.getBody()), InitResponse.class);
    }

    /**
     * Notify mobile side once certain actions have been taken on web side.
     *
     * @param hostUrl     hostUrl
     * @param baseRequest baseRequest
     * @param userName    the userName of the user
     * @param code        {@link StatusNotifyCode}
     * @return the http response body
     * @throws IOException if the http response body can't be convert to {@link StatusNotifyResponse}
     */
    StatusNotifyResponse statusNotify(String hostUrl, BaseRequest baseRequest, String userName, int code) throws IOException {
        String rnd = String.valueOf(System.currentTimeMillis());
        final String url = String.format(jeevesProperties.getUrl().getStatusNotify(), hostUrl);
        StatusNotifyRequest request = new StatusNotifyRequest();
        request.setBaseRequest(baseRequest);
        request.setFromUserName(userName);
        request.setToUserName(userName);
        request.setCode(code);
        request.setClientMsgId(rnd);
        HttpHeaders customHeader = new HttpHeaders();
        customHeader.set(HttpHeaders.REFERER, hostUrl + "/");
        customHeader.setOrigin(hostUrl);
        HeaderUtils.assign(customHeader, postHeader);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(request, customHeader), String.class);
        return jsonMapper.readValue(WechatUtils.textDecode(responseEntity.getBody()), StatusNotifyResponse.class);
    }

    /**
     * report stats to server
     */
    void statReport() {
        StatReportRequest request = new StatReportRequest();
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setUin("");
        baseRequest.setSid("");
        request.setBaseRequest(baseRequest);
        request.setCount(0);
        request.setList(new StatReport[0]);
        HttpHeaders customHeader = new HttpHeaders();
        customHeader.set(HttpHeaders.REFERER, jeevesProperties.getUrl().getEntry());
        customHeader.setOrigin(jeevesProperties.getUrl().getEntry().replaceAll("/$", ""));
        HeaderUtils.assign(customHeader, postHeader);
        restTemplate.exchange(jeevesProperties.getUrl().getStatReport(), HttpMethod.POST, new HttpEntity<>(request, customHeader), String.class);
    }

    /**
     * Get all the contacts. If the Seq it returns is greater than zero, it means at least one more request is required to fetch all contacts.
     *
     * @param hostUrl hostUrl
     * @param skey    skey
     * @param seq     seq
     * @return contact information
     * @throws IOException if the http response body can't be convert to {@link GetContactResponse}
     */
    GetContactResponse getContact(String hostUrl, String skey, long seq) throws IOException {
        long rnd = System.currentTimeMillis();
        final String url = String.format(jeevesProperties.getUrl().getGetContact(), hostUrl, rnd, seq, escape(skey));
        HttpHeaders customHeader = new HttpHeaders();
        customHeader.setAccept(Arrays.asList(MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN, MediaType.ALL));
        customHeader.set(HttpHeaders.REFERER, hostUrl + "/");
        HeaderUtils.assign(customHeader, getHeader);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(customHeader), String.class);
        return jsonMapper.readValue(WechatUtils.textDecode(responseEntity.getBody()), GetContactResponse.class);
    }

    /**
     * Get all the members in the given chatrooms
     *
     * @param hostUrl     hostUrl
     * @param baseRequest baseRequest
     * @param list        chatroom information
     * @return chatroom members information
     * @throws IOException if the http response body can't be convert to {@link BatchGetContactResponse}
     */
    BatchGetContactResponse batchGetContact(String hostUrl, BaseRequest baseRequest, ChatRoomDescription[] list) throws IOException {
        long rnd = System.currentTimeMillis();
        String url = String.format(jeevesProperties.getUrl().getBatchGetContact(), hostUrl, rnd);
        BatchGetContactRequest request = new BatchGetContactRequest();
        request.setBaseRequest(baseRequest);
        request.setCount(list.length);
        request.setList(list);
        HttpHeaders customHeader = createPostCustomHeader();
        HeaderUtils.assign(customHeader, postHeader);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(request, customHeader), String.class);
        return jsonMapper.readValue(WechatUtils.textDecode(responseEntity.getBody()), BatchGetContactResponse.class);
    }

    /**
     * Periodically request to server
     *
     * @param hostUrl hostUrl
     * @param uin     uin
     * @param sid     sid
     * @param skey    skey
     * @param syncKey syncKey
     * @return synccheck response
     * @throws IOException        if the http response body can't be convert to {@link SyncCheckResponse}
     * @throws URISyntaxException if url is invalid
     */
    SyncCheckResponse syncCheck(String hostUrl, String uin, String sid, String skey, SyncKey syncKey) throws IOException, URISyntaxException {
        String path = String.format(jeevesProperties.getUrl().getSyncCheck(), hostUrl);
        URI uri = UriComponentsBuilder.fromHttpUrl(path)
                .queryParam("uin", uin)
                .queryParam("sid", sid)
                .queryParam("skey", skey)
                .queryParam("deviceid", DeviceIdGenerator.generate())
                .queryParam("synckey", syncKey.toString())
                .queryParam("r", String.valueOf(System.currentTimeMillis()))
                .queryParam("_", String.valueOf(System.currentTimeMillis()))
                .build().toUri();
        HttpHeaders customHeader = new HttpHeaders();
        customHeader.setAccept(Arrays.asList(MediaType.ALL));
        customHeader.set(HttpHeaders.REFERER, hostUrl + "/");
        HeaderUtils.assign(customHeader, getHeader);
        ResponseEntity<String> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<>(customHeader), String.class);
        String body = responseEntity.getBody();
        Matcher matcher = JeevesProperties.SYNC_CHECK_PATTERN.matcher(body);
        if (!matcher.find()) {
            return null;
        } else {
            SyncCheckResponse result = new SyncCheckResponse();
            result.setRetcode(Integer.valueOf(matcher.group(1)));
            result.setSelector(Integer.valueOf(matcher.group(2)));
            return result;
        }
    }

    /**
     * Sync with server to get new messages and contacts
     *
     * @param hostUrl     hostUrl
     * @param syncKey     syncKey
     * @param baseRequest baseRequest
     * @return new messages and contacts
     * @throws IOException if the http response body can't be convert to {@link SyncResponse}
     */
    SyncResponse sync(String hostUrl, SyncKey syncKey, BaseRequest baseRequest) throws IOException {
        final String url = String.format(jeevesProperties.getUrl().getSync(), hostUrl, baseRequest.getSid(), escape(baseRequest.getSkey()));
        SyncRequest request = new SyncRequest();
        request.setBaseRequest(baseRequest);
        request.setRr(-System.currentTimeMillis() / 1000);
        request.setSyncKey(syncKey);
        HttpHeaders customHeader = createPostCustomHeader();
        HeaderUtils.assign(customHeader, postHeader);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(request, customHeader), String.class);
        return jsonMapper.readValue(WechatUtils.textDecode(responseEntity.getBody()), SyncResponse.class);
    }

    VerifyUserResponse acceptFriend(String hostUrl, BaseRequest baseRequest, String passTicket, VerifyUser[] verifyUsers) throws IOException, URISyntaxException {
        int[] sceneList = new int[]{AddScene.WEB.getCode()};
        String path = String.format(jeevesProperties.getUrl().getVerifyUser(), hostUrl);
        VerifyUserRequest request = new VerifyUserRequest();
        request.setBaseRequest(baseRequest);
        request.setOpcode(VerifyUserOPCode.VERIFYOK.getCode());
        request.setSceneList(sceneList);
        request.setSceneListCount(sceneList.length);
        request.setSkey(baseRequest.getSkey());
        request.setVerifyContent("");
        request.setVerifyUserList(verifyUsers);
        request.setVerifyUserListSize(verifyUsers.length);

        URI uri = UriComponentsBuilder.fromHttpUrl(path)
                .queryParam("r", String.valueOf(System.currentTimeMillis()))
                .queryParam("pass_ticket", passTicket)
                .build().toUri();
        ResponseEntity<String> responseEntity = restTemplate.exchange(uri, HttpMethod.POST, new HttpEntity<>(request, this.postHeader), String.class);
        return jsonMapper.readValue(WechatUtils.textDecode(responseEntity.getBody()), VerifyUserResponse.class);
    }

    SendMsgResponse sendText(String hostUrl, BaseRequest baseRequest, String content, String fromUserName, String toUserName) throws IOException {
        final int scene = 0;
        final String rnd = String.valueOf(System.currentTimeMillis() * 10);
        final String url = String.format(jeevesProperties.getUrl().getSendMsg(), hostUrl);
        SendMsgRequest request = new SendMsgRequest();
        request.setBaseRequest(baseRequest);
        request.setScene(scene);
        BaseMsg msg = new BaseMsg();
        msg.setType(MessageType.TEXT.getCode());
        msg.setClientMsgId(rnd);
        msg.setContent(content);
        msg.setFromUserName(fromUserName);
        msg.setToUserName(toUserName);
        msg.setLocalID(rnd);
        request.setMsg(msg);
        HttpHeaders customHeader = createPostCustomHeader();
        HeaderUtils.assign(customHeader, postHeader);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(request, customHeader), String.class);
        return jsonMapper.readValue(WechatUtils.textDecode(responseEntity.getBody()), SendMsgResponse.class);
    }

    OpLogResponse setAlias(String hostUrl, BaseRequest baseRequest, String newAlias, String userName) throws IOException {
        final int cmdId = OpLogCmdId.MODREMARKNAME.getCode();
        final String url = String.format(jeevesProperties.getUrl().getOpLog(), hostUrl);
        OpLogRequest request = new OpLogRequest();
        request.setBaseRequest(baseRequest);
        request.setCmdId(cmdId);
        request.setRemarkName(newAlias);
        request.setUserName(userName);
        HttpHeaders customHeader = createPostCustomHeader();
        HeaderUtils.assign(customHeader, postHeader);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(request, customHeader), String.class);
        return jsonMapper.readValue(WechatUtils.textDecode(responseEntity.getBody()), OpLogResponse.class);
    }

    CreateChatRoomResponse createChatRoom(String hostUrl, BaseRequest baseRequest, String[] userNames, String topic) throws IOException {
        String rnd = String.valueOf(System.currentTimeMillis());
        final String url = String.format(jeevesProperties.getUrl().getCreateChatRoom(), hostUrl, rnd);
        CreateChatRoomRequest request = new CreateChatRoomRequest();
        request.setBaseRequest(baseRequest);
        request.setMemberCount(userNames.length);
        ChatRoomMember[] members = new ChatRoomMember[userNames.length];
        for (int i = 0; i < userNames.length; i++) {
            members[i] = new ChatRoomMember();
            members[i].setUserName(userNames[i]);
        }
        request.setMemberList(members);
        request.setTopic(topic);
        HttpHeaders customHeader = createPostCustomHeader();
        HeaderUtils.assign(customHeader, postHeader);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(request, customHeader), String.class);
        return jsonMapper.readValue(WechatUtils.textDecode(responseEntity.getBody()), CreateChatRoomResponse.class);
    }

    DeleteChatRoomMemberResponse deleteChatRoomMember(String hostUrl, BaseRequest baseRequest, String chatRoomUserName, String userName) throws IOException {
        final String url = String.format(jeevesProperties.getUrl().getDeleteChatRoomMember(), hostUrl);
        DeleteChatRoomMemberRequest request = new DeleteChatRoomMemberRequest();
        request.setBaseRequest(baseRequest);
        request.setChatRoomName(chatRoomUserName);
        request.setDelMemberList(userName);
        HttpHeaders customHeader = createPostCustomHeader();
        HeaderUtils.assign(customHeader, postHeader);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(request, customHeader), String.class);
        return jsonMapper.readValue(WechatUtils.textDecode(responseEntity.getBody()), DeleteChatRoomMemberResponse.class);
    }

    AddChatRoomMemberResponse addChatRoomMember(String hostUrl, BaseRequest baseRequest, String chatRoomUserName, String userName) throws IOException {
        final String url = String.format(jeevesProperties.getUrl().getAddChatRoomMember(), hostUrl);
        AddChatRoomMemberRequest request = new AddChatRoomMemberRequest();
        request.setBaseRequest(baseRequest);
        request.setChatRoomName(chatRoomUserName);
        request.setAddMemberList(userName);
        HttpHeaders customHeader = createPostCustomHeader();
        HeaderUtils.assign(customHeader, postHeader);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(request, customHeader), String.class);
        return jsonMapper.readValue(WechatUtils.textDecode(responseEntity.getBody()), AddChatRoomMemberResponse.class);
    }

    byte[] downloadImage(String url) {
        HttpHeaders customHeader = new HttpHeaders();
        customHeader.set("Accept", "image/webp,image/apng,image/*,*/*;q=0.8");
        customHeader.set("Referer", this.refererValue);
        HeaderUtils.assign(customHeader, getHeader);
        ResponseEntity<byte[]> responseEntity = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(customHeader), new ParameterizedTypeReference<byte[]>() {
        });
        return responseEntity.getBody();
    }

    private String escape(String str) throws IOException {
        return URLEncoder.encode(str, StandardCharsets.UTF_8.toString());
    }

    private void appendAdditionalCookies(CookieStore store, Map<String, String> cookies, String domain, String path, Date expiryDate) {
        cookies.forEach((key, value) -> {
            BasicClientCookie cookie = new BasicClientCookie(key, value);
            cookie.setDomain(domain);
            cookie.setPath(path);
            cookie.setExpiryDate(expiryDate);
            store.addCookie(cookie);
        });
    }

    private HttpHeaders createPostCustomHeader() {
        HttpHeaders customHeader = new HttpHeaders();
        customHeader.setOrigin(this.originValue);
        customHeader.set(HttpHeaders.REFERER, this.refererValue);
        return customHeader;
    }
}