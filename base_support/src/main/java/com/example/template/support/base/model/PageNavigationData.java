package com.example.template.support.base.model;

import android.content.Intent;
import android.os.Bundle;

/**
 * Created by Horrarndoo on 2018/11/2.
 * <p>
 * 页面跳转实体类，主要用于处理MVVM下的ViewModel和页面交互跳转逻辑
 * <p>
 * ViewModel通过改变这个实体类的值回调View的观察者，然后View观察者回调中统一处理跳转逻辑
 */
public class PageNavigationData {
    public static final int TARGET_TYPE_ACTIVITY_CLASS = 1;
    public static final int TARGET_TYPE_ACTIVITY_URL = 2;
    public static final int TARGET_TYPE_FRAGMENT_CLASS = 3;
    public static final int TARGET_TYPE_FRAGMENT_URL = 4;
    public static final int TARGET_TYPE_ACTIVITY_EXTERNAL = 5;

    public int type;
    public Class<?> targetClass;
    public String targetPath;
    public Bundle bundle;
    public Intent intent;

    /**
     * 通过url路径跳转Activity或者Fragment的构造方法
     *
     * @param type      TARGET_TYPE_ACTIVITY_URL 或者 TARGET_TYPE_FRAGMENT_URL
     * @param targetUrl 目标页面url路径
     * @param bundle    携带的bundle数据
     */
    public PageNavigationData(int type, String targetUrl, Bundle bundle) {
        this.type = type;
        this.targetPath = targetUrl;
        this.bundle = bundle;
    }

    /**
     * 通过Class跳转Activity或者Fragment的构造方法
     *
     * @param type        TARGET_TYPE_ACTIVITY_CLASS 或者 TARGET_TYPE_FRAGMENT_CLASS
     * @param targetClass 目标页面class
     * @param bundle      携带的bundle数据
     */
    public PageNavigationData(int type, Class<?> targetClass, Bundle bundle) {
        this.type = type;
        this.targetClass = targetClass;
        this.bundle = bundle;
    }

    /**
     * 跳转外部Activity使用的构造方法
     *
     * @param intent intent
     */
    public PageNavigationData(Intent intent) {
        this.type = TARGET_TYPE_ACTIVITY_EXTERNAL;
        this.intent = intent;
    }

    //==================== 禁止其他类重写以下构造方法 =====================//
    private PageNavigationData(int type, Class<?> targetClass, String targetUrl, Bundle bundle, Intent
            intent) {
    }

    private PageNavigationData(int type, Class<?> targetClass, String targetUrl, Bundle bundle) {
    }

    private PageNavigationData(int type, Class<?> targetClass, String targetUrl, Intent intent) {
    }

    private PageNavigationData(int type, Class<?> targetClass, Intent intent) {
    }

    private PageNavigationData(int type, String targetUrl, Intent intent) {
    }

    private PageNavigationData(int type) {
    }

    private PageNavigationData() {
    }
}
