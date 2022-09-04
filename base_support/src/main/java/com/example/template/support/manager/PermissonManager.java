package com.example.template.support.manager;

import java.util.List;

import androidx.annotation.NonNull;

/**
 * Created by Horrarndoo on 2022/9/3.
 * <p>
 * 权限管理
 */
public class PermissonManager {
    private static volatile PermissonManager instance;

    private PermissonManager() {
    }

    /**
     * 单一实例
     */
    public static PermissonManager getInstance() {
        if (instance == null) {
            synchronized (PermissonManager.class) {
                if (instance == null)
                    instance = new PermissonManager();
            }
        }
        return instance;
    }

    /**
     * 权限申请被拒绝的监听
     */
    private OnPermissionDeniedListener sOnPermissionDeniedListener;

    //============动态申请权限失败事件设置=============//

    /**
     * 设置权限申请被拒绝的监听
     *
     * @param listener 权限申请被拒绝的监听器
     */
    public void setOnPermissionDeniedListener(@NonNull OnPermissionDeniedListener listener) {
        sOnPermissionDeniedListener = listener;
    }

    public OnPermissionDeniedListener getOnPermissionDeniedListener() {
        return sOnPermissionDeniedListener;
    }

    /**
     * 权限申请被拒绝的监听
     */
    public interface OnPermissionDeniedListener {
        /**
         * 权限申请被拒绝
         */
        void onDenied(List<String> permissionsDenied);
    }
}
