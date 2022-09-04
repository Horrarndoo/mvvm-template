package com.example.template.common.global;

import android.content.Intent;

import com.example.template.common.service.CommonService;
import com.example.template.support.base.global.BaseApplication;

/**
 * Created by Horrarndoo on 2022/9/1.
 * <p>
 * 通用Application
 */
public class CommonApplication extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        Intent intent = new Intent(this, CommonService.class);
        startService(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopService(new Intent(this, CommonService.class));
    }
}
