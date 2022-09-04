package com.example.template.support.utils;

/**
 * Created by Horrarndoo on 2019/3/14.
 * <p>
 * 基本数据类型转换工具类
 */
public class DataTypeUtils {
    public static byte[] intToBytes(int number, int byteLength) {
        byte[] b = new byte[byteLength];
        for (int i = 0; i < b.length; i++) {
            b[i] = Integer.valueOf(number & 0xff).byteValue();// 将最低位保存在最低位
            number = number >> 8; // 向右移8位
        }
        return b;
    }

    public static int bytesToInt(byte[] bytes) {
        int number = 0;
        for (int i = 0; i < bytes.length; i++) {
            int temp = bytes[i] & 0xff;
            temp <<= i * 8;
            number |= temp;
        }
        return number;
    }

    /**
     * 截取double数据
     *
     * @param d        double数据
     * @param endIndex 截取到小数点后几位
     * @return 截取小数点后endIndex位后的double数据
     */
    public static double getSubDouble(double d, int endIndex) {
        try {
            String s = String.valueOf(d);
            if (s.length() - s.indexOf(".") <= endIndex)
                return d;

            String s1 = s.substring(0, s.indexOf(".") + endIndex + 1);
            return Double.valueOf(s1);
        } catch (Exception e) {
            e.printStackTrace();
            return d;
        }
    }

    /**
     * 小数转换为int，直接double转为int会丢失部分精度，会导致误差
     *
     * @param d 小数
     * @return 小数*100000
     */
    public static int decimalsToInt(double d) {
        d = getSubDouble(d, 5);
        String s = String.valueOf(d);
        int pointIndex = s.indexOf(".");
        if (pointIndex == -1)
            return (int) d;

        StringBuilder sb = new StringBuilder(s);
        StringBuilder sb2 = new StringBuilder(s.substring(pointIndex + 1, s.length()));
        if (sb2.length() < 5) {
            for (int i = 0; i < 5 - sb2.length(); i++) {
                sb.append("0");
            }
        }
        sb.deleteCharAt(pointIndex);
        return Integer.valueOf(sb.toString());
    }

    /**
     * int转换为小数，保留小数点后几位，直接用int->double会丢失部分精度，会导致误差
     *
     * @param i          整数
     * @param pointIndex 小数点后几位
     * @return 转换后的小数
     */
    public static double intToDecimals(int i, int pointIndex) {
        String s = String.valueOf(i);
        StringBuilder sb = new StringBuilder(s);

        int index = sb.length() - pointIndex;
        while (index <= 0) {
            sb.insert(0, "0");
            index = sb.length() - pointIndex;
        }

        sb.insert(sb.length() - pointIndex, ".");
        return Double.valueOf(sb.toString());
    }
}
