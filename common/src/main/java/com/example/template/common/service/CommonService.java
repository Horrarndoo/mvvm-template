package com.example.template.common.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.example.template.common.manager.CommonStatusManager;
import com.example.template.common.manager.LocationManager;
import com.example.template.support.utils.LogUtils;

import androidx.annotation.Nullable;

/**
 * Created by Horrarndoo on 2022/9/1.
 * <p>
 * 通用服务（包含本机位置、全局状态、硬件状态等）
 */
public class CommonService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtils.d("onCreate");
        //位置管理初始化
        LocationManager.getInstance().init();
        //全局状态管理初始化
        CommonStatusManager.getInstance().init();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtils.d("onDestroy");
        //位置管理销毁
        LocationManager.getInstance().destroy();
        //全局状态管理销毁
        CommonStatusManager.getInstance().destroy();
    }
}
