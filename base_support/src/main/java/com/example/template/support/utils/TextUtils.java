package com.example.template.support.utils;

/**
 * Created by Horrarndoo on 2018/12/10.
 * <p>
 */
public class TextUtils {
    public static boolean isEmpty(String str) {
        return str == null || str.equals("") || str.length() == 0;
    }

    /**
     * 是否中文字符
     *
     * @param c 字符
     * @return 是否中文字符
     */
    public static boolean isChChar(char c) {
        final String illeageChChar = "。，《》｛｝~@#￥%…&*（），。？！‘…";
        if (illeageChChar.indexOf(c) != -1) {
            return false;
        }

        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == Character.UnicodeBlock
                .CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub ==
                Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub == Character
                .UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
            return true;
        }
        return false;
    }

    /**
     * 获取绝对的字符长度（中文字符按两个长度计算）
     *
     * @param strName 字符串
     * @return 绝对的字符长度，-1代表有中文和字母以外的字符
     */
    public static int getAbsLen(String strName) {
        int len = 0;
        char[] ch = strName.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            if (TextUtils.isChChar(c)) {
                len += 2;
            } else if (('0' <= c && c <= '9') || ('A' <= c && c <= 'Z') || ('a' <= c && c <= 'z')
                    || ('_' == c)) {
                ++len;
            } else
                return -1;
        }
        return len;
    }

    /**
     * 获取字符串中的整型字符，拼接成长整型返回
     *
     * @param s 字符串
     * @return 字符串中的整型字符，拼接成长整型返回
     */
    public static long getLongInString(String s) {
        StringBuilder sb = new StringBuilder();
        for (char c : s.toCharArray()) {
            try {
                int i = Integer.valueOf(c + "");
                sb.append(i);
            } catch (Exception ignored) {
            }
        }

        try {
            return sb.length() > 0 ? Long.valueOf(sb.toString()) : 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
