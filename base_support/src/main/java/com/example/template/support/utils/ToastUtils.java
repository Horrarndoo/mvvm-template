package com.example.template.support.utils;

import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.template.support.annotation.MainThread;
import com.example.template.support.base.global.BaseApplication;

/**
 * Created by Horrarndoo on 2022/9/2.
 * <p>
 * toast工具类封装
 */
public class ToastUtils {
    public static final float TOAST_FONT_SIZE_BIG = 24;
    public static final float TOAST_FONT_SIZE_NORMAL = 16;

    /**
     * 显示一个toast提示
     *
     * @param resouceId toast字符串资源id
     */
    public static void showToast(int resouceId) {
        showToast(ResourcesUtils.getString(resouceId));
    }

    /**
     * 显示一个toast提示
     *
     * @param text toast字符串
     */
    public static void showToast(String text) {
        showToast(text, Toast.LENGTH_SHORT);
    }

    /**
     * 显示一个toast提示
     *
     * @param resouceId toast字符串资源id
     * @param duration  toast显示时间
     */
    public static void showToast(int resouceId, int duration) {
        showToast(ResourcesUtils.getString(resouceId), duration);
    }


    /**
     * 显示一个toast提示
     *
     * @param text     toast字符串
     * @param duration toast显示时间
     */
    public static void showToast(String text, int duration) {
        showToast(text, duration, TOAST_FONT_SIZE_NORMAL);
    }

    /**
     * 显示一个toast提示
     *
     * @param text     toast字符串
     * @param fontSize 字体大小（单位:sp）
     */
    public static void showToast(String text, float fontSize) {
        showToast(text, Toast.LENGTH_SHORT, fontSize);
    }

    /**
     * 显示一个toast提示
     *
     * @param text     toast字符串
     * @param duration toast显示时间
     * @param fontSize 字体大小（单位:sp）
     */
    @MainThread
    public static void showToast(String text, int duration, float fontSize) {
        Toast toast = Toast.makeText(BaseApplication.getContext(), text, duration);
        ViewGroup vg = (ViewGroup) toast.getView();
        TextView tvText = (TextView) vg.getChildAt(0);
        tvText.setTextSize(fontSize);
        toast.show();
    }
}
