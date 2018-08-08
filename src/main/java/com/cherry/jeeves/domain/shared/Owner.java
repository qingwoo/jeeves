package com.cherry.jeeves.domain.shared;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Owner extends Member {
    @JsonProperty
    private int WebWxPluginSwitch;
    @JsonProperty
    private int HeadImgFlag;

    public int getWebWxPluginSwitch() {
        return WebWxPluginSwitch;
    }

    public Owner setWebWxPluginSwitch(int webWxPluginSwitch) {
        WebWxPluginSwitch = webWxPluginSwitch;
        return this;
    }

    public int getHeadImgFlag() {
        return HeadImgFlag;
    }

    public Owner setHeadImgFlag(int headImgFlag) {
        HeadImgFlag = headImgFlag;
        return this;
    }
}