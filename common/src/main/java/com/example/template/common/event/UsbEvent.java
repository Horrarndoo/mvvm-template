package com.example.template.common.event;

/**
 * Created by Horrarndoo on 2019/9/27.
 * <p>
 * USB事件
 */
public class UsbEvent {
    private int eventId;

    public UsbEvent(int eventId) {
        this.eventId = eventId;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }
}
