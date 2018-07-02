package org.banyan.common.codec.service;

import org.banyan.common.codec.util.AESUtils;
import org.banyan.common.codec.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Properties;

/**
 * Copyright (C), 2018, Banyan Network Foundation
 * EncryptService
 *
 * @author Kevin Huang
 * @version 2017年09月13日 14:57:00
 */
public class EncryptService {
    private static Logger LOGGER = LoggerFactory.getLogger(EncryptService.class);
    private static Properties properties = new Properties();
    private static IvParameterSpec iv;
    private static SecretKeySpec password;
    private static Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

    static {
        try {
            InputStream inputStream = EncryptService.class.getClassLoader().getResourceAsStream("properties/encrypt.properties");
            properties.load(inputStream);
            iv = new IvParameterSpec(properties.getProperty("codec.iv").getBytes(DEFAULT_CHARSET));
            password = AESUtils.generateKey(properties.getProperty("codec.password").getBytes(DEFAULT_CHARSET));
        } catch (Exception e) {
            LOGGER.info("加载配置文件异常", e);
        }
    }

    public static String encryptCard(String str) {
        return encrypt(str, EncryptType.CARD);
    }

    public static String encryptMobile(String str) {
        return encrypt(str, EncryptType.MOBILE);
    }

    public static String encryptIDCard(String str) {
        return encrypt(str, EncryptType.ID_CARD);
    }

    public static String encryptOther(String str) {
        return encrypt(str, EncryptType.OTHER);
    }

    public static String encrypt(String str, EncryptType encryptType) {
        String result = null;

        if (StringUtils.hasText(str) && null != encryptType) {
            try {
                byte[] bytes = AESUtils.encrypt(str.trim().getBytes(DEFAULT_CHARSET), iv, password);
                if (null != bytes) {
                    String value = StringUtils.byteToStr(bytes);
                    if (StringUtils.hasText(value)) {
                        result = String.format("%s%s", value, encryptType.getSuffix());
                    }
                }
            } catch (Exception e) {
                LOGGER.info("加密异常", e);
            }
        }

        return result;
    }

    public static String decrypt(String str) {
        String result = null;
        if (StringUtils.hasText(str) && str.trim().length() > 1) {
            String suffix = str.trim().substring(str.trim().length() - 1);
            EncryptType encryptType = EncryptType.getEncryptType(suffix);
            if (null != encryptType) {
                result = decrypt(str, encryptType);
            }
        }
        return result;
    }

    public static String decrypt(String str, EncryptType encryptType) {
        String result = null;

        if (StringUtils.hasText(str) && str.trim().length() > 1 && null != encryptType) {
            String text = str.trim();
            if (text.endsWith(encryptType.getSuffix())) {
                text = text.substring(0, text.length() - 1);
                try {
                    byte[] bytes = AESUtils.decrypt(StringUtils.strToByte(text), iv, password);
                    if (null != bytes) {
                        String value = new String(bytes, DEFAULT_CHARSET);
                        if (StringUtils.hasText(value)) {
                            result = value;
                        }
                    }
                } catch (Exception e) {
                    LOGGER.info("解密异常", e);
                }
            } else {
                LOGGER.info("解密错误: 解密字符串和解密类型不匹配");
            }

        }

        return result;
    }
}
