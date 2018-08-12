package com.cherry.jeeves.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.IOException;

/**
 * Jackson常用API工具
 *
 * @author tangjialin on 2018-07-16.
 */
public final class XmlUtils {
    private static final XmlMapper XML_MAPPER = new XmlMapper();

    /**
     * 不可实例化
     */
    private XmlUtils() {
    }

    public static XmlMapper getXmlMapper() {
        return XML_MAPPER;
    }

    /**
     * 将JSON字符串转换为对象
     *
     * @param xml       JSON字符串
     * @param valueType 类型
     * @return 对象
     */
    public static <T> T toObject(String xml, Class<T> valueType) {
        try {
            return XML_MAPPER.readValue(xml, valueType);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * 将JSON字符串转换为对象
     * <pre>
     *     List<MyDto> asList = XmlUtils.toObject(xml, new TypeReference<List<MyDto>>() { });
     *     Map<String, Object> asMap = XmlUtils.toObject(xml, new TypeReference<Map<String, Object>>() { });
     * </pre>
     *
     * @param xml          JSON字符串
     * @param valueTypeRef 类型
     * @return 对象
     */
    public static <T> T toObject(String xml, TypeReference<T> valueTypeRef) {
        try {
            return XML_MAPPER.readValue(xml, valueTypeRef);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

}