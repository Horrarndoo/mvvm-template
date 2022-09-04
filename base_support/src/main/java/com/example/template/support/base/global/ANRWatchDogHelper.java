package com.example.template.support.base.global;

import com.example.template.support.utils.LogUtils;
import com.github.anrwatchdog.ANRError;
import com.github.anrwatchdog.ANRWatchDog;

/**
 * Created by Horrarndoo on 2022/8/30.
 * <p>
 * ANR看门狗监听器初始化
 */
public final class ANRWatchDogHelper {

    private static final String TAG = "ANRWatchDog";

    private ANRWatchDogHelper() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * ANR看门狗
     */
    private static ANRWatchDog sANRWatchDog;

    /**
     * ANR监听触发的时间
     */
    private static final int ANR_DURATION = 4000;

    /**
     * ANR静默处理【就是不处理，直接记录一下日志】
     */
    private final static ANRWatchDog.ANRListener SILENT_LISTENER = new ANRWatchDog.ANRListener() {
        @Override
        public void onAppNotResponding(ANRError error) {
            LogUtils.e("onAppNotResponding");
        }
    };


    /**
     * ANR自定义处理【可以是记录日志用于上传】
     */
    private final static ANRWatchDog.ANRListener CUSTOM_LISTENER = error -> {
        LogUtils.e("Detected Application Not Responding!", error);
        //这里进行ANR的捕获后的操作

        throw error;
    };

    public static void init() {
        //这里设置监听的间隔为2秒
        sANRWatchDog = new ANRWatchDog(2000);
        sANRWatchDog.setANRInterceptor(duration -> {
            long ret = ANR_DURATION - duration;
            if (ret > 0) {
                LogUtils.w("Intercepted ANR that is too short (" + duration + " ms), " +
                        "postponing for " + ret + " ms.");
            }
            //当返回是0或者负数时，就会触发ANR监听回调
            return ret;
        }).setANRListener(SILENT_LISTENER).start();
    }

    public static ANRWatchDog getANRWatchDog() {
        return sANRWatchDog;
    }
}
