package com.cherry.jeeves.domain.shared;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RecommendInfo {
    @JsonProperty
    private String UserName;
    @JsonProperty
    private String NickName;
    @JsonProperty
    private long QQNum;
    @JsonProperty
    private String Province;
    @JsonProperty
    private String City;
    @JsonProperty
    private String Content;
    @JsonProperty
    private String Signature;
    @JsonProperty
    private String Alias;
    @JsonProperty
    private int Scene;
    @JsonProperty
    private int VerifyFlag;
    @JsonProperty
    private long AttrStatus;
    @JsonProperty
    private int Sex;
    @JsonProperty
    private String Ticket;
    @JsonProperty
    private int OpCode;

    public String getUserName() {
        return UserName;
    }

    public RecommendInfo setUserName(String userName) {
        UserName = userName;
        return this;
    }

    public String getNickName() {
        return NickName;
    }

    public RecommendInfo setNickName(String nickName) {
        NickName = nickName;
        return this;
    }

    public long getQQNum() {
        return QQNum;
    }

    public RecommendInfo setQQNum(long QQNum) {
        this.QQNum = QQNum;
        return this;
    }

    public String getProvince() {
        return Province;
    }

    public RecommendInfo setProvince(String province) {
        Province = province;
        return this;
    }

    public String getCity() {
        return City;
    }

    public RecommendInfo setCity(String city) {
        City = city;
        return this;
    }

    public String getContent() {
        return Content;
    }

    public RecommendInfo setContent(String content) {
        Content = content;
        return this;
    }

    public String getSignature() {
        return Signature;
    }

    public RecommendInfo setSignature(String signature) {
        Signature = signature;
        return this;
    }

    public String getAlias() {
        return Alias;
    }

    public RecommendInfo setAlias(String alias) {
        Alias = alias;
        return this;
    }

    public int getScene() {
        return Scene;
    }

    public RecommendInfo setScene(int scene) {
        Scene = scene;
        return this;
    }

    public int getVerifyFlag() {
        return VerifyFlag;
    }

    public RecommendInfo setVerifyFlag(int verifyFlag) {
        VerifyFlag = verifyFlag;
        return this;
    }

    public long getAttrStatus() {
        return AttrStatus;
    }

    public RecommendInfo setAttrStatus(long attrStatus) {
        AttrStatus = attrStatus;
        return this;
    }

    public int getSex() {
        return Sex;
    }

    public RecommendInfo setSex(int sex) {
        Sex = sex;
        return this;
    }

    public String getTicket() {
        return Ticket;
    }

    public RecommendInfo setTicket(String ticket) {
        Ticket = ticket;
        return this;
    }

    public int getOpCode() {
        return OpCode;
    }

    public RecommendInfo setOpCode(int opCode) {
        OpCode = opCode;
        return this;
    }
}
