package com.example.template.support.utils;

/**
 * Created by Horrarndoo on 2019/3/21.
 * <p>
 */
public class LegalCheckUtils {
    public static boolean isLegalCoordinate(String coordinate) {
        //        String s = String.valueOf(coordinate);
        if (!coordinate.contains("."))
            return true;

        String s2 = coordinate.substring(coordinate.indexOf(".") + 1, coordinate.length());
        return s2.length() <= 5;
    }

    public static boolean isLegalData(double data, int legalLength) {
        String s = String.valueOf(data);
        if (!s.contains("."))
            return true;

        String s2 = s.substring(s.indexOf(".") + 1, s.length());
        return s2.length() <= legalLength;
    }

    /**
     * 浮点字符串是否是符合指定小数点位数的值
     *
     * @param str   浮点字符串
     * @param digit 小数点后位数
     * @return 浮点字符串是否是符合指定小数点位数的值
     */
    public static boolean isFitDotDigitDecimal(String str, int digit) {
        //        String s = String.valueOf(coordinate);
        if (!str.contains("."))
            return true;

        String s2 = str.substring(str.indexOf(".") + 1, str.length());
        return s2.length() <= digit;
    }

    public static String toLeagalDecimal(String str) {
        if (str.length() == 1) {
            if (str.equals("+") || str.equals("-"))
                return str;
        }
        str = str.replace("+", "");
        if (str.contains(".")) {
            if (str.lastIndexOf(".") == str.length() - 1 && str.length() > 1) {
                str = str.replace(".", "");
            }
        }
        return str;
    }

    public static boolean isLeagalDecimal(String str) {
        if (str.length() == 0)
            return false;

        if (str.length() == 1 && str.equals("+"))
            return false;

        if (str.length() == 1 && str.equals("-"))
            return false;

        if (str.contains("+."))
            return false;

        if (str.contains("-."))
            return false;

        if (str.charAt(0) == '.')
            return false;

        if (str.charAt(str.length() - 1) == '.')
            return false;

        if (str.length() >= 2 && str.charAt(0) == '0' && str.charAt(1) != '.')
            return false;

        return !str.equals("-") && !str.equals(".");
    }

    /**
     * 是否合法的位置信息
     *
     * @param longitude 经度
     * @param latitude  纬度
     * @return 是否合法的位置信息
     */
    public static boolean isLeagalLocation(double longitude, double latitude) {
        return Math.abs(longitude) < 180 && Math.abs(latitude) < 90;
    }
}
