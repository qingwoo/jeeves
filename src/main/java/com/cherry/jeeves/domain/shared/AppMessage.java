package com.cherry.jeeves.domain.shared;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AppMessage {
    private String title;
    private String des;
    private String username;
    private String action;
    private int type;
    @JacksonXmlProperty(localName = "showtype")
    private int showType;
    private String content;
    private String url;
    private String lowurl;
    private String dataurl;
    private String lowdataurl;
    @JacksonXmlProperty(localName = "contentattr")
    private int contentAttr;
    @JacksonXmlProperty(localName = "template_id")
    private String templateId;
    private String md5;

    public String getTitle() {
        return title;
    }

    public AppMessage setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDes() {
        return des;
    }

    public AppMessage setDes(String des) {
        this.des = des;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public AppMessage setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getAction() {
        return action;
    }

    public AppMessage setAction(String action) {
        this.action = action;
        return this;
    }

    public int getType() {
        return type;
    }

    public AppMessage setType(int type) {
        this.type = type;
        return this;
    }

    public int getShowType() {
        return showType;
    }

    public AppMessage setShowType(int showType) {
        this.showType = showType;
        return this;
    }

    public String getContent() {
        return content;
    }

    public AppMessage setContent(String content) {
        this.content = content;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public AppMessage setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getLowurl() {
        return lowurl;
    }

    public AppMessage setLowurl(String lowurl) {
        this.lowurl = lowurl;
        return this;
    }

    public String getDataurl() {
        return dataurl;
    }

    public AppMessage setDataurl(String dataurl) {
        this.dataurl = dataurl;
        return this;
    }

    public String getLowdataurl() {
        return lowdataurl;
    }

    public AppMessage setLowdataurl(String lowdataurl) {
        this.lowdataurl = lowdataurl;
        return this;
    }

    public int getContentAttr() {
        return contentAttr;
    }

    public AppMessage setContentAttr(int contentAttr) {
        this.contentAttr = contentAttr;
        return this;
    }

    public String getTemplateId() {
        return templateId;
    }

    public AppMessage setTemplateId(String templateId) {
        this.templateId = templateId;
        return this;
    }

    public String getMd5() {
        return md5;
    }

    public AppMessage setMd5(String md5) {
        this.md5 = md5;
        return this;
    }
}
