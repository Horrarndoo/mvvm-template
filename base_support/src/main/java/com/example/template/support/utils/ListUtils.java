package com.example.template.support.utils;

import java.util.List;

/**
 * Created by Horrarndoo on 2022/9/3.
 * <p>
 * 集合工具类
 */
public class ListUtils {
    /**
     * 根据分隔符将List转换为String
     *
     * @param list list
     * @return list转换的字符串
     */
    public static String listToString(final List<String> list) {
        return listToString(list, ",");
    }

    /**
     * 根据分隔符将List转换为String
     *
     * @param list      list
     * @param separator 分隔符
     * @return list转换的字符串
     */
    public static String listToString(final List<String> list, final String separator) {
        if (list == null || list.size() == 0) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i)).append(separator);
        }
        return sb.toString().substring(0, sb.toString().length() - 1);
    }
}
