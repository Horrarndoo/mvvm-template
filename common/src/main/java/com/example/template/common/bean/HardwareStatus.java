package com.example.template.common.bean;

/**
 * Created by Horrarndoo on 2022/9/1.
 * <p>
 * 硬件状态（温度、电池状态等）
 */
public class HardwareStatus {
    public static final String DEFAULT_FULL_STATUS = "未获取到状态";

    public static final float DEFAULT_TEMP = -270.f;

    public static final String TEMP_UNIT = "℃";

    /**
     * CPU温度报警阈值
     */
    public static final float CPU_TEMP_GIVE_AN_ALARM_THRESHOLD = 100;
    /**
     * 电池温度报警阈值
     */
    public static final float BATTERY_TEMP_GIVE_AN_ALARM_THRESHOLD = 62;

    /**
     * CPU正常温度阈值
     */
    public static final float CPU_TEMP_NORMAL_THRESHOLD = 95;
    /**
     * 电池正常温度阈值
     */
    public static final float BATTERY_TEMP_NORMAL_THRESHOLD = 58;

    /**
     * 无线模块温度报警阈值
     */
    public static final float WIRLESS_MODULE_TEMP_GIVE_AN_ALARM_THRESHOLD = 50;

    /**
     * 低电告警值
     */
    public static final float LOW_BATTERY_WARNING_LEVEL = 15;

    /**
     * cpu温度
     */
    public float cpuTemperature = DEFAULT_TEMP;
    /**
     * 电池温度
     */
    public float batteryTemperature = DEFAULT_TEMP;
    /**
     * 电池安装状态
     */
    public boolean batteryPresent = true;
    /**
     * 电池完整状态
     */
    public String batteryFullStatus = DEFAULT_FULL_STATUS;
    /**
     * 电池充电状态
     */
    public int batteryChargedStatus = 1;
    /**
     * 电池电量
     */
    public int batteryExtraLevel = 0;
}
