package com.cherry.jeeves.utils;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

/**
 * Jackson常用API工具
 *
 * @author tangjialin on 2018-07-16.
 */
public final class XmlUtils {
    private static final XmlMapper XML_MAPPER = new XmlMapper();

    public static XmlMapper getXmlMapper() {
        return XML_MAPPER;
    }

    /**
     * 不可实例化
     */
    private XmlUtils() {
    }

}