package com.cherry.jeeves.service;

import com.cherry.jeeves.domain.request.component.BaseRequest;
import com.cherry.jeeves.domain.shared.Contact;
import com.cherry.jeeves.domain.shared.Owner;
import com.cherry.jeeves.domain.shared.SyncCheckKey;
import com.cherry.jeeves.domain.shared.SyncKey;
import com.cherry.jeeves.utils.DeviceIdGenerator;
import com.cherry.jeeves.utils.JsonUtils;
import com.cherry.jeeves.utils.WechatUtils;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 信息缓存服务
 *
 * @author tangjialin on 2018-08-11.
 */
@Component
public class CacheService {

    public void reset() {
        this.alive = false;
        this.uuid = null;
        this.hostUrl = null;
        this.syncUrl = null;
        this.fileUrl = null;
        this.passTicket = null;
        this.baseRequest = null;
        this.owner = null;
        this.syncKey = null;
        this.syncCheckKey = null;
        this.sKey = null;
        this.uin = null;
        this.sid = null;
        this.allAccounts.clear();
        this.individuals.clear();
        this.mediaPlatforms.clear();
        this.chatRooms.clear();
    }

    private boolean alive = false;
    private String uuid;
    private String hostUrl;
    private String syncUrl;
    private String fileUrl;
    private String passTicket;
    private BaseRequest baseRequest;
    private Owner owner;
    private SyncKey syncKey;
    private SyncCheckKey syncCheckKey;
    private String sKey;
    private String uin;
    private String sid;

    private Map<String, Contact> allAccounts = new HashMap<>();
    private Set<Contact> individuals = new HashSet<>();
    private Set<Contact> mediaPlatforms = new HashSet<>();
    private Set<Contact> chatRooms = new HashSet<>();

    private Set<String> contactNamesWithUnreadMessage = new HashSet<>();

    public boolean isAlive() {
        return alive;
    }

    public CacheService setAlive(boolean alive) {
        this.alive = alive;
        return this;
    }

    public String getHostUrl() {
        return hostUrl;
    }

    public CacheService setHostUrl(String hostUrl) {
        this.hostUrl = hostUrl;
        return this;
    }

    public String getPassTicket() {
        return passTicket;
    }

    public CacheService setPassTicket(String passTicket) {
        this.passTicket = passTicket;
        return this;
    }

    public BaseRequest getBaseRequest() {
        baseRequest.setDeviceID(DeviceIdGenerator.generate());
        return baseRequest;
    }

    public CacheService setBaseRequest(BaseRequest baseRequest) {
        this.baseRequest = baseRequest;
        return this;
    }

    public Owner getOwner() {
        return owner;
    }

    public CacheService setOwner(Owner owner) {
        this.owner = owner;
        return this;
    }

    public SyncKey getSyncKey() {
        return syncKey;
    }

    public CacheService setSyncKey(SyncKey syncKey) {
        this.syncKey = syncKey;
        return this;
    }

    public SyncCheckKey getSyncCheckKey() {
        return syncCheckKey;
    }

    public CacheService setSyncCheckKey(SyncCheckKey syncCheckKey) {
        this.syncCheckKey = syncCheckKey;
        return this;
    }

    public String getUuid() {
        return uuid;
    }

    public CacheService setUuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public String getsKey() {
        return sKey;
    }

    public CacheService setsKey(String sKey) {
        this.sKey = sKey;
        return this;
    }

    public String getUin() {
        return uin;
    }

    public CacheService setUin(String uin) {
        this.uin = uin;
        return this;
    }

    public String getSid() {
        return sid;
    }

    public CacheService setSid(String sid) {
        this.sid = sid;
        return this;
    }

    public Map<String, Contact> getAllAccounts() {
        return allAccounts;
    }

    public Set<Contact> getIndividuals() {
        return individuals;
    }

    public Set<Contact> getMediaPlatforms() {
        return mediaPlatforms;
    }

    public Set<Contact> getChatRooms() {
        return chatRooms;
    }

    public String getSyncUrl() {
        return syncUrl;
    }

    public CacheService setSyncUrl(String syncUrl) {
        this.syncUrl = syncUrl;
        return this;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public CacheService setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
        return this;
    }

    public Set<String> getContactNamesWithUnreadMessage() {
        return contactNamesWithUnreadMessage;
    }

    public CacheService setContacts(Set<Contact> contacts) {
        individuals.clear();
        chatRooms.clear();
        mediaPlatforms.clear();
        allAccounts.clear();

        Owner owner = getOwner();
        if (owner == null) { return this; }
        allAccounts.put(owner.getUserName(), JsonUtils.toObject(owner, Contact.class));
        for (Contact contact : contacts) {
            allAccounts.put(contact.getUserName(), contact);
            if (WechatUtils.isIndividual(contact)) {
                individuals.add(contact);
            } else if (WechatUtils.isMediaPlatform(contact)) {
                mediaPlatforms.add(contact);
            } else if (WechatUtils.isChatRoom(contact)) {
                chatRooms.add(contact);
            }
        }
        return this;
    }
}