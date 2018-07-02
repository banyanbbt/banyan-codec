package org.banyan.common.codec.util;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Copyright (C), 2018, Banyan Network Foundation
 * AESUtils调用工具类
 *
 * @author Kevin Huang
 * @version 2017年9月13日 上午8:45:57
 */
public class AESUtils {
    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";

    /**
     * 执行加密
     *
     * @param content  待加密内容
     * @param iv       向量
     * @param password 密钥
     * @return String 加密后密文
     * @throws Exception 异常
     */
    public static byte[] encrypt(byte[] content, byte[] iv, byte[] password) throws Exception {
        return encrypt(content, new IvParameterSpec(iv), generateKey(password));
    }

    /**
     * 执行加密
     *
     * @param content 待加密内容
     * @param key     密钥规范
     * @param iv      初始化向量
     * @return String 加密后密文
     * @throws Exception 异常
     */
    public static byte[] encrypt(byte[] content, IvParameterSpec iv, SecretKeySpec key) throws Exception {
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.ENCRYPT_MODE, key, iv);
        return cipher.doFinal(content);
    }

    /**
     * 执行解密
     *
     * @param content  待加密内容
     * @param iv       向量
     * @param password 密钥
     * @return String 解密后明文
     * @throws Exception 异常
     */
    public static byte[] decrypt(byte[] content, byte[] iv, byte[] password) throws Exception {
        return decrypt(content, new IvParameterSpec(iv), generateKey(password));
    }

    /**
     * 执行解密
     *
     * @param content 待加密内容
     * @param key     密钥规范
     * @param iv      初始化向量
     * @return String 加密后密文
     * @throws Exception 异常
     */
    public static byte[] decrypt(byte[] content, IvParameterSpec iv, SecretKeySpec key) throws Exception {
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.DECRYPT_MODE, key, iv);
        return cipher.doFinal(content);
    }

    /**
     * 将byte 转为 SecretKeySpec
     *
     * @param key byte
     * @return SecretKeySpec
     */
    public static SecretKeySpec generateKey(byte[] key) {
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, ALGORITHM);
        return secretKeySpec;
    }
}