package com.example.template.common.event;


import com.example.template.common.bean.HardwareStatus;

/**
 * Created by Horrarndoo on 2019/11/13.
 * <p>
 * 硬件状态事件
 */
public class HardwareStatusEvent {
    private int eventId;
    private HardwareStatus hardwareStatus;

    public HardwareStatusEvent(int eventId, HardwareStatus hardwareStatus) {
        this.eventId = eventId;
        this.hardwareStatus = hardwareStatus;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public HardwareStatus getHardwareStatus() {
        return hardwareStatus;
    }

    public void setHardwareStatus(HardwareStatus hardwareStatus) {
        this.hardwareStatus = hardwareStatus;
    }
}
