package com.example.template.support.utils;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * Created by Horrarndoo on 2018/6/5.
 * <p>
 * 显示相关工具类
 */
public class DisplayUtils {
    /**
     * 将px值转换为dp值
     *
     * @param context context
     * @param pxValue px值
     * @return 转换后的dp值
     */
    public static int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将dp值转换为px值
     *
     * @param context context
     * @param dpValue dp值
     * @return 转换后的px值
     */
    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 将px值转换为sp值
     *
     * @param context context
     * @param pxValue px值
     * @return 转换后的sp值
     */
    public static int px2sp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将sp值转换为px值
     *
     * @param context context
     * @param spValue sp值
     * @return 转换后的px值
     */
    public static int sp2px(Context context, float spValue) {
        final float scale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * scale + 0.5f);
    }

    /**
     * 获取屏幕高度
     *
     * @param activity activity
     * @return 屏幕高度
     */
    public static int getScreenHeightPixels(Activity activity) {
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics.heightPixels;
    }

    /**
     * 获取屏幕宽度
     *
     * @param activity activity
     * @return 屏幕宽度
     */
    public static int getScreenWidthPixels(Activity activity) {
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics.widthPixels;
    }

    /**
     * 获取屏幕宽度
     *
     * @param context context
     * @return 屏幕宽度
     */
    public static int getScreenWidthPixels(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (wm == null)
            return 0;

        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    /**
     * 获取屏幕高度
     *
     * @param context context
     * @return 屏幕高度
     */
    public static int getScreenHeightPixels(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (wm == null)
            return 0;

        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }

    /**
     * 当前是否横屏
     *
     * @param activity Activity
     * @return 当前是否横屏
     */
    public static boolean isLandscape(Activity activity) {
        return getScreenWidthPixels(activity) - getScreenHeightPixels(activity) > 0;
    }

    /**
     * 当前是否横屏
     *
     * @param context context
     * @return 当前是否横屏
     */
    public static boolean isLandscape(Context context) {
        return getScreenWidthPixels(context) - getScreenHeightPixels(context) > 0;
    }

    // 获取状态栏高度
    public static int getStatusBarHeight(Context context) {
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen",
                "android");
        return context.getResources().getDimensionPixelSize(resourceId);
    }


    // 获取导航栏高度
    public static int getNavigationBarHeight(Context context) {
        int rid = context.getResources().getIdentifier("config_showNavigationBar", "bool",
                "android");
        if (rid != 0) {
            int resourceId = context.getResources().getIdentifier("navigation_bar_height",
                    "dimen", "android");
            return context.getResources().getDimensionPixelSize(resourceId);
        } else
            return 0;

    }
}
