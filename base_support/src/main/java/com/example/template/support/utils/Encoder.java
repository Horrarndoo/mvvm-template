package com.example.template.support.utils;

import java.security.MessageDigest;

/**
 * Created by Horrarndoo on 2018/11/2.
 * <p>
 * 账户管理者
 */
public class Encoder {

    private static final String ALGORITHM_MD5 = "MD5";
    public static final String ALGORITHM_SHA1 = "SHA1";

    private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /**
     * 加密
     *
     * @param str 需要加密的明文
     * @return 加密后的密文
     */
    public static String encode(String algorithm, String str) {
        if (str == null) {
            return null;
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            messageDigest.update(str.getBytes());
            return getFormattedText(messageDigest.digest());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * 使用MD5加密
     *
     * @param str 需要加密的明文
     * @return 加密后的密文
     */
    public static String encodeByMD5(String str) {
        return encode(ALGORITHM_MD5, str);
    }

    /**
     * 使用SHA1加密
     *
     * @param str 需要加密的明文
     * @return 加密后的密文
     */
    public static String encodeBySHA1(String str) {
        return encode(ALGORITHM_SHA1, str);
    }

    private static String getFormattedText(byte[] bytes) {
        int len = bytes.length;
        StringBuilder buf = new StringBuilder(len * 2);
        for (int j = 0; j < len; j++) {
            buf.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]);
            buf.append(HEX_DIGITS[bytes[j] & 0x0f]);
        }
        return buf.toString();
    }

}
