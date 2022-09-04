package com.example.template.common.runnable;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;

import com.example.template.common.bean.HardwareStatus;
import com.example.template.common.broadcastreceiver.BatteryReceiver;
import com.example.template.common.constants.MMKVKey;
import com.example.template.common.global.CommonApplication;
import com.example.template.support.constants.Charsets;
import com.example.template.support.helper.MMKVHelper;
import com.example.template.support.utils.LogUtils;

import java.io.FileInputStream;
import java.util.Calendar;

/**
 * Created by Horrarndoo on 2019/11/13.
 * <p>
 * 硬件状态更新线程
 */
public class HwStatusUpdateRunnable implements Runnable {
    private boolean isStop = false;
    private final static int CHECK_INTEVAL = 10000; //10s更新一次状态
    private BatteryReceiver mBatteryReceiver;
    private final HardwareStatus mHardwareStatus;
    private OnHwStatusChangeListener mHwStatusChangeListener;

    public HwStatusUpdateRunnable(OnHwStatusChangeListener listener) {
        mHardwareStatus = new HardwareStatus();
        mHwStatusChangeListener = listener;
        initBatteryReceiver();
    }

    @Override
    public void run() {
        while (!isStop) {
            try {
                //读取CPU温度
                readCpuTemperature();

                //如果时间没有同步/设置过，根据gps设置系统时间
                if (!MMKVHelper.getInstance().getBoolean(MMKVKey.KEY_IS_TIME_SYNC))
                    setSystemTime();

                Thread.sleep(CHECK_INTEVAL);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 停止runnable
     */
    public void setStop() {
        isStop = true;
        mHwStatusChangeListener = null;
        CommonApplication.getContext().unregisterReceiver(mBatteryReceiver);
    }

    /**
     * 设置系统时间
     */
    private void setSystemTime() {
        LocationManager locationManager =
                (LocationManager) CommonApplication.getContext().getSystemService(Context
                        .LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        if (locationManager == null)
            return;

        String provider = locationManager.getBestProvider(criteria, false);
        if (provider != null) {
            @SuppressLint("MissingPermission")
            Location location = locationManager.getLastKnownLocation(provider);
            if (location != null) {
                //设置过一次系统时间后，更新时间同步/设置的标志
                MMKVHelper.getInstance().putBoolean(MMKVKey.KEY_IS_TIME_SYNC, true);
                Calendar c = Calendar.getInstance();
                c.setTimeInMillis(location.getTime());
                AlarmManager alarmManager =
                        ((AlarmManager) CommonApplication.getContext().getSystemService(Context.ALARM_SERVICE));
                if (alarmManager != null) {
                    LogUtils.d("自动校准GPS时间");
                    alarmManager.setTime(location.getTime());
                }
            }
        }
    }

    /**
     * 读取CPU温度
     */
    private void readCpuTemperature() {
        String cpuTempFilePath = "/sys/class/thermal/thermal_zone1/temp";
        float cpuTemp = 0;
        try {
            FileInputStream fis = new FileInputStream(cpuTempFilePath);
            byte[] buffer = new byte[fis.available()];
            int len = fis.read(buffer);
            cpuTemp = Float.parseFloat(new String(buffer, 0, len, Charsets.GBK)) / 1000.f;
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (mHardwareStatus.cpuTemperature != cpuTemp) {
            //            LogUtils.d("cpu温度变化，当前cpu温度 = " + cpuTemp);
            mHardwareStatus.cpuTemperature = cpuTemp;
            notifyHardwareStatusChanged();
        }
    }

    /**
     * 初始化电池广播接收器
     */
    private void initBatteryReceiver() {
        IntentFilter mBatteryFilter = new IntentFilter();
        mBatteryFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
        mBatteryFilter.addAction(Intent.ACTION_BATTERY_LOW);
        mBatteryFilter.addAction(Intent.ACTION_BATTERY_OKAY);
        mBatteryFilter.addAction(Intent.ACTION_POWER_CONNECTED);

        mBatteryReceiver = new BatteryReceiver();
        mBatteryReceiver.setOnBatteryStatusChangeListener(new BatteryReceiver.OnBatteryStatusChangeListener() {
            @Override
            public void onChanged(HardwareStatus hardwareStatus) {
                mHardwareStatus.batteryExtraLevel = hardwareStatus.batteryExtraLevel;
                mHardwareStatus.batteryTemperature = hardwareStatus.batteryTemperature;
                mHardwareStatus.batteryPresent = hardwareStatus.batteryPresent;
                mHardwareStatus.batteryChargedStatus = hardwareStatus.batteryChargedStatus;
                mHardwareStatus.batteryFullStatus = hardwareStatus.batteryFullStatus;
                notifyHardwareStatusChanged();
            }
        });
        CommonApplication.getContext().registerReceiver(mBatteryReceiver, mBatteryFilter);
    }

    /**
     * 通知硬件状态变化
     */
    private void notifyHardwareStatusChanged() {
        if (mHwStatusChangeListener != null) {
            mHwStatusChangeListener.onChanged(mHardwareStatus);
        }
    }

    /**
     * 硬件状态变化监听
     */
    public interface OnHwStatusChangeListener {
        /**
         * 变化
         *
         * @param hardwareStatus 电池状态
         */
        void onChanged(HardwareStatus hardwareStatus);
    }
}
