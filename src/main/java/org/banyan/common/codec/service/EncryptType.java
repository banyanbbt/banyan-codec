package org.banyan.common.codec.service;

/**
 * Copyright (C), 2018, Banyan Network Foundation
 * Encrypt Type
 *
 * @author Kevin Huang
 * @version 2016年8月4日 下午6:01:33
 */
public enum EncryptType {
    CARD("C"), MOBILE("M"), ID_CARD("I"), OTHER("O");

    private String suffix;

    EncryptType(String suffix) {
        this.suffix = suffix;
    }

    public String getSuffix() {
        return suffix;
    }

    public static EncryptType getEncryptType(String suffix) {
        EncryptType encryptType = null;
        if (CARD.suffix.equals(suffix)) {
            encryptType = CARD;
        } else if (MOBILE.suffix.equals(suffix)) {
            encryptType = MOBILE;
        } else if (ID_CARD.suffix.equals(suffix)) {
            encryptType = ID_CARD;
        } else if (OTHER.suffix.equals(suffix)) {
            encryptType = OTHER;
        }
        return encryptType;
    }
}
