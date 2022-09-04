package com.example.template.support.utils;

import android.util.Log;

/**
 * Created by Horrarndoo on 2019/11/25.
 * <p>
 */
public class ArrayUtils {
    /**
     * 获取数组中首个与输入值相同的值的下标
     *
     * @param t     输入值
     * @param array 判定数组
     * @param <T>   数组类型
     * @return 数组中首个与输入值相同的值的下标（如果数组中没有此值返回-1）
     */
    public static <T> int getObjectFirstIndex(T t, T[] array) {
        if (array == null) {
            Log.e("ArrayUtils", "array is null.");
            return -1;
        }
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(t))
                return i;
        }
        return -1;
    }
}
