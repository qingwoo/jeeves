package com.cherry.jeeves.domain.shared;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * <pre>
 * <msg>
 *   <appmsg appid="wx020a535dccd46c11" sdkver="0">
 *     <title>Vue、React、Angular最佳UI框架 - Fundebug - 简书</title>
 *     <des>我正在看【Vue、React、Angular最佳UI框架 - Fundebug - 简书】，分享给你，一起看吧！</des>
 *     <username></username>
 *     <action>view</action>
 *     <type>5</type>
 *     <showtype>0</showtype>
 *     <content></content>
 *     <url>https://www.jianshu.com/p/0051e626b88b</url>
 *     <lowurl></lowurl>
 *     <dataurl></dataurl>
 *     <lowdataurl></lowdataurl>
 *     <contentattr>0</contentattr>
 *     <streamvideo>
 *       <streamvideourl></streamvideourl>
 *       <streamvideototaltime>0</streamvideototaltime>
 *       <streamvideotitle></streamvideotitle>
 *       <streamvideowording></streamvideowording>
 *       <streamvideoweburl></streamvideoweburl>
 *       <streamvideothumburl></streamvideothumburl>
 *       <streamvideoaduxinfo></streamvideoaduxinfo>
 *       <streamvideopublishid></streamvideopublishid>
 *     </streamvideo>
 *     <canvasPageItem>
 *       <canvasPageXml>
 *         <![CDATA[]]>
 *       </canvasPageXml>
 *     </canvasPageItem>
 *     <appattach>
 *       <attachid></attachid>
 *       <cdnthumburl>305c0201000455305302010002042bf4b1ec02033d14b902041ee6607102045b703295042e6175706170706d73675f616531346335316235613639663635325f313533343037393633333738335f33313232310204010800030201000400</cdnthumburl>
 *       <cdnthumbmd5>88e44c7e80531c6d419b4bd12631213b</cdnthumbmd5>
 *       <cdnthumblength>14836</cdnthumblength>
 *       <cdnthumbheight>80</cdnthumbheight>
 *       <cdnthumbwidth>120</cdnthumbwidth>
 *       <cdnthumbaeskey>434a56804eca49c48f66c4d570bbf99d</cdnthumbaeskey>
 *       <aeskey>434a56804eca49c48f66c4d570bbf99d</aeskey>
 *       <encryver>1</encryver>
 *       <fileext></fileext>
 *       <islargefilemsg>0</islargefilemsg>
 *     </appattach>
 *     <extinfo></extinfo>
 *     <androidsource>3</androidsource>
 *     <thumburl></thumburl>
 *     <mediatagname></mediatagname>
 *     <messageaction>
 *       <![CDATA[]]>
 *     </messageaction>
 *     <messageext>
 *       <![CDATA[]]>
 *     </messageext>
 *     <emoticongift>
 *       <packageflag>0</packageflag>
 *       <packageid></packageid>
 *     </emoticongift>
 *     <emoticonshared>
 *       <packageflag>0</packageflag>
 *       <packageid></packageid>
 *     </emoticonshared>
 *     <designershared>
 *       <designeruin>0</designeruin>
 *       <designername>null</designername>
 *       <designerrediretcturl>null</designerrediretcturl>
 *     </designershared>
 *     <emotionpageshared>
 *       <tid>0</tid>
 *       <title>null</title>
 *       <desc>null</desc>
 *       <iconUrl>null</iconUrl>
 *       <secondUrl>null</secondUrl>
 *       <pageType>0</pageType>
 *     </emotionpageshared>
 *     <webviewshared>
 *       <shareUrlOriginal></shareUrlOriginal>
 *       <shareUrlOpen></shareUrlOpen>
 *       <jsAppId></jsAppId>
 *       <publisherId></publisherId>
 *     </webviewshared>
 *     <template_id></template_id>
 *     <md5>88e44c7e80531c6d419b4bd12631213b</md5>
 *     <weappinfo>
 *       <username></username>
 *       <appid></appid>
 *       <appservicetype>0</appservicetype>
 *     </weappinfo>
 *     <statextstr>GhQKEnd4MDIwYTUzNWRjY2Q0NmMxMQ==</statextstr>
 *     <websearch>
 *       <rec_category>0</rec_category>
 *     </websearch>
 *   </appmsg>
 *   <fromusername></fromusername>
 *   <scene>0</scene>
 *   <appinfo>
 *     <version>2</version>
 *     <appname>UC浏览器</appname>
 *   </appinfo>
 *   <commenturl></commenturl>
 * </msg>
 * </pre>
 *
 * @author tangjialin on 2018-08-12.
 */
@JacksonXmlRootElement(localName = "msg")
@JsonIgnoreProperties(ignoreUnknown = true)
public class LinkContent {
    private AppMessage appmsg;

    public AppMessage getAppmsg() {
        return appmsg;
    }

    public LinkContent setAppmsg(AppMessage appmsg) {
        this.appmsg = appmsg;
        return this;
    }
}
