package com.example.template.common.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;

import com.example.template.common.bean.HardwareStatus;

/**
 * Created by Horrarndoo on 2022/9/1.
 * <p>
 * 电池广播接收器（拦截电池状态变化）
 */
public class BatteryReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        StringBuilder sb = new StringBuilder();
        String action = intent.getAction();

        if (Intent.ACTION_BATTERY_CHANGED.equals(action)) {
            HardwareStatus hardwareStatus = new HardwareStatus();
            sb.append("======================");
            int extralLevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
            hardwareStatus.batteryExtraLevel = extralLevel;
            sb.append("\n当前电量:").append(extralLevel);
            sb.append("\n电池电量:").append(intent.getIntExtra(BatteryManager
                    .EXTRA_SCALE, 100));
            // 电池伏数
            sb.append("\n当前电压：").append(intent.getIntExtra(BatteryManager
                    .EXTRA_VOLTAGE, 0));
            // 电池温度
            float batteryTemperature = intent.getIntExtra(BatteryManager
                    .EXTRA_TEMPERATURE, 0) / 10.f;
            hardwareStatus.batteryTemperature = batteryTemperature;
            sb.append("\n当前温度：").append(batteryTemperature);
            boolean isPresent = intent.getBooleanExtra(BatteryManager.EXTRA_PRESENT, true);
            sb.append("\n测到电池：").append(isPresent);
            hardwareStatus.batteryPresent = isPresent;
            int batteryChargedStatus = intent.getIntExtra("status",
                    BatteryManager.BATTERY_STATUS_UNKNOWN);
            hardwareStatus.batteryChargedStatus = batteryChargedStatus;
            String batteryStatus = null;
            switch (batteryChargedStatus) {
                case BatteryManager.BATTERY_STATUS_CHARGING:
                    batteryStatus = "充电状态";
                    break;
                case BatteryManager.BATTERY_STATUS_DISCHARGING:
                    batteryStatus = "放电状态";
                    break;
                case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
                    batteryStatus = "未充电";
                    break;
                case BatteryManager.BATTERY_STATUS_FULL:
                    batteryStatus = "充满电";
                    break;
                case BatteryManager.BATTERY_STATUS_UNKNOWN:
                    batteryStatus = "未知状态";
                    break;
            }
            sb.append("\n当前状态：").append(batteryStatus);
            String BatteryStatus2 = null;
            switch (intent.getIntExtra(BatteryManager.EXTRA_PLUGGED,
                    BatteryManager.BATTERY_PLUGGED_AC)) {
                case BatteryManager.BATTERY_PLUGGED_AC:
                    BatteryStatus2 = "AC充电";
                    break;
                case BatteryManager.BATTERY_PLUGGED_USB:
                    BatteryStatus2 = "USB充电";
                    break;
            }
            sb.append("\n充电方式：").append(BatteryStatus2);
            String BatteryTemp = null;
            switch (intent.getIntExtra(BatteryManager.EXTRA_HEALTH,
                    BatteryManager.BATTERY_HEALTH_UNKNOWN)) {
                case BatteryManager.BATTERY_HEALTH_UNKNOWN:
                    BatteryTemp = "未知错误";
                    break;
                case BatteryManager.BATTERY_HEALTH_GOOD:
                    BatteryTemp = "状态良好";
                    break;
                case BatteryManager.BATTERY_HEALTH_DEAD:
                    BatteryTemp = "电池没有电";
                    break;
                case BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE:
                    BatteryTemp = "电池电压过高";
                    break;
                case BatteryManager.BATTERY_HEALTH_OVERHEAT:
                    BatteryTemp = "电池过热";
                    break;
            }
            sb.append("\n电池状态：").append(BatteryTemp);
            sb.append("\n======================");
            //                    LogUtils.d(sb.toString());
            hardwareStatus.batteryFullStatus = String.valueOf(sb
                    .toString());

            if (mOnBatteryStatusChangeListener != null) {
                mOnBatteryStatusChangeListener.onChanged(hardwareStatus);
            }
        }
    }

    private OnBatteryStatusChangeListener mOnBatteryStatusChangeListener;

    public void setOnBatteryStatusChangeListener(OnBatteryStatusChangeListener listener) {
        mOnBatteryStatusChangeListener = listener;
    }

    /**
     * 电池状态变化监听
     */
    public interface OnBatteryStatusChangeListener {
        /**
         * 变化
         *
         * @param hardwareStatus 电池状态
         */
        void onChanged(HardwareStatus hardwareStatus);
    }
}
