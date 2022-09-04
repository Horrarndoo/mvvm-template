package com.example.template.common.constants;

/**
 * Created by Horrarndoo on 2022/9/1.
 * <p>
 * 事件ID
 */
public interface EventId {
    int KEY_PTT_DOWN = 901;
    int KEY_PTT_UP = 902;
    int KEY_DESTROY_DOWN = 903;
    int KEY_DESTROY_UP = 904;
    int KEY_DESTROY_GROUP_DOWN = 905;
    int KEY_DESTROY_GROUP_UP = 906;
    int KEY_VOLUME_UP_UP = 907;
    int KEY_VOLUME_UP_DOWN = 908;

    /**
     * USB设备被拔出（主要用于本机作为host被接入U盘导入地图）
     */
    int USB_STATE_EJECTING = 1001;
    /**
     * USB已断开连接（主要用于本机USB线接电脑导入地图）
     */
    int USB_STATE_DISCONNECTED = 1002;

    /**
     * 用户在网信息位置变化
     */
    int USER_ONLINE_LOCATION_CHANGED = 3001;
    /**
     * 用户在网状态变化
     */
    int USER_ONLINE_STATE_CHANGED = 3002;
    /**
     * 用户未读信息数变化
     */
    int USER_ONLINE_UNREAD_MSG_COUNT_CHANGED = 3003;
    /**
     * 新用户上线
     */
    int NEW_USER_ONLINE = 3004;
    /**
     * 用户在线人数变化
     */
    int USER_ONLINE_COUNT_CHANGED = 3005;
    /**
     * 设备硬件状态变化
     */
    int HARDWARE_STATUS_CHANGED = 8001;

    /**
     * 时间已同步
     */
    int TIME_IS_SYNC = 9002;

    /**
     * 本机位置变化
     */
    int LOCATION_CHANGED = 13001;
}
