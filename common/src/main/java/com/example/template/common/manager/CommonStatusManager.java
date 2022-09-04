package com.example.template.common.manager;

import com.example.template.base_support.BuildConfig;
import com.example.template.common.bean.HardwareStatus;
import com.example.template.common.constants.EventId;
import com.example.template.common.constants.VolumeInfo;
import com.example.template.common.event.HardwareStatusEvent;
import com.example.template.common.runnable.HwStatusUpdateRunnable;
import com.example.template.support.manager.ThreadManager;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Horrarndoo on 2022/9/1.
 * <p>
 * 全局状态管理（USB、温度、电池等）
 */
public class CommonStatusManager {
    /**
     * 硬件状态任务（定期检测电池、CPU相关状态）
     */
    private HwStatusUpdateRunnable mHwStatusUpdateRunnable;
    private static volatile CommonStatusManager instance;
    /**
     * usb状态
     */
    private final int mUsbState;
    /**
     * 硬件状态
     */
    private final HardwareStatus mHardwareStatus;

    private CommonStatusManager() {
        mUsbState = VolumeInfo.STATE_UNMOUNTED;
        mHardwareStatus = new HardwareStatus();
    }

    public static CommonStatusManager getInstance() {
        if (instance == null) {
            synchronized (CommonStatusManager.class) {
                if (instance == null)
                    instance = new CommonStatusManager();
            }
        }
        return instance;
    }

    public int getUsbState() {
        return mUsbState;
    }

    public HardwareStatus getHardwareStatus() {
        return mHardwareStatus;
    }

    /**
     * 初始化位置线程（在Application中初始化或者在Service中初始化）
     */
    public void init() {
        //debug模式下不启动硬件状态更新任务
        if (BuildConfig.DEBUG)
            return;

        if (mHwStatusUpdateRunnable != null)
            return;

        mHwStatusUpdateRunnable =
                new HwStatusUpdateRunnable(new HwStatusUpdateRunnable.OnHwStatusChangeListener() {
                    @Override
                    public void onChanged(HardwareStatus hardwareStatus) {
                        EventBus.getDefault().post(new HardwareStatusEvent(EventId.HARDWARE_STATUS_CHANGED,
                                mHardwareStatus));
                    }
                });
        ThreadManager.getThreadPool().execute(mHwStatusUpdateRunnable);
    }

    /**
     * 销毁
     */
    public void destroy() {
        if (mHwStatusUpdateRunnable != null) {
            mHwStatusUpdateRunnable.setStop();
            ThreadManager.getThreadPool().cancel(mHwStatusUpdateRunnable);
            mHwStatusUpdateRunnable = null;
        }
        instance = null;
    }
}
