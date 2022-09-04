package com.example.template.common.manager;

import com.example.template.common.bean.LocationInfo;
import com.example.template.common.constants.EventId;
import com.example.template.common.event.LocationEvent;
import com.example.template.common.runnable.LocationUpdateRunnable;
import com.example.template.support.manager.ThreadManager;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Horrarndoo on 2022/9/1.
 * <p>
 * 本机位置管理者（缓存最新的（真实）位置信息，供其他模块获取）
 */
public class LocationManager {
    private static volatile LocationManager instance;
    //本机位置信息
    private final LocationInfo mLocationInfo;
    //锁
    private final Object mLock = new Object();
    //位置更新任务
    private LocationUpdateRunnable mLocationUpdateRunnable;

    private LocationManager() {
        mLocationInfo = new LocationInfo();
    }

    public static LocationManager getInstance() {
        if (instance == null) {
            synchronized (LocationManager.class) {
                if (instance == null)
                    instance = new LocationManager();
            }
        }
        return instance;
    }

    /**
     * 返回GPS模块的位置信息
     *
     * @return GPS模块的位置信息
     */
    public LocationInfo getLocationInfo() {
        synchronized (mLock) {
            return mLocationInfo;
        }
    }

    /**
     * 初始化位置线程（在Application中初始化或者在Service中初始化）
     */
    public void init() {
        if (mLocationUpdateRunnable != null)
            return;

        mLocationUpdateRunnable =
                new LocationUpdateRunnable(new LocationUpdateRunnable.OnLocationChangeListener() {
            @Override
            public void onChanged(LocationInfo locationInfo) {
                synchronized (mLock) {
                    if(mLocationInfo.equals(locationInfo)){
                        return;
                    }

                    mLocationInfo.longitude = locationInfo.longitude;
                    mLocationInfo.latitude = locationInfo.latitude;
                    mLocationInfo.altitude = locationInfo.altitude;
                    mLocationInfo.accuracy = locationInfo.accuracy;
                    //发送位置变化事件
                    EventBus.getDefault().post(new LocationEvent(EventId.LOCATION_CHANGED,
                            locationInfo));
                }
            }
        });
        ThreadManager.getThreadPool().execute(mLocationUpdateRunnable);
    }

    /**
     * 销毁
     */
    public void destroy() {
        if (mLocationUpdateRunnable != null) {
            mLocationUpdateRunnable.setStop();
            ThreadManager.getThreadPool().cancel(mLocationUpdateRunnable);
            mLocationUpdateRunnable = null;
        }
        instance = null;
    }
}
