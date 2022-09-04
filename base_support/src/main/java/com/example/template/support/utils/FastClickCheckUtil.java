package com.example.template.support.utils;

import android.view.View;

import com.example.template.base_support.R;

import androidx.annotation.NonNull;

/**
 * Created by Horrarndoo on 2022/8/31.
 * <p>
 * 快速点击检验
 */
public class FastClickCheckUtil {
    /**
     * 判断是否属于快速点击
     *
     * @param view     点击的View
     * @param interval 快速点击的阈值
     * @return true：快速点击
     */
    public static boolean isFastClick(@NonNull View view, long interval) {
        int key = R.id.view_click_time;

        // 最近的点击时间
        long currentClickTime = System.currentTimeMillis();

        if (null == view.getTag(key)) {
            // 1. 第一次点击
            // 保存最近点击时间
            view.setTag(key, currentClickTime);
            return false;
        }
        // 2. 非第一次点击

        // 上次点击时间
        long lastClickTime = (long) view.getTag(key);
        if (currentClickTime - lastClickTime < interval) {
            // 未超过时间间隔，视为快速点击
            return true;
        } else {
            // 保存最近点击时间
            view.setTag(key, currentClickTime);
            return false;
        }
    }
}
