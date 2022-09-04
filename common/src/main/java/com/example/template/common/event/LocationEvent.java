package com.example.template.common.event;

import com.example.template.common.bean.LocationInfo;

/**
 * Created by Horrarndoo on 2022/9/1.
 * <p>
 * 位置事件
 */
public class LocationEvent {
    private int eventId;
    private LocationInfo locationInfo;

    public LocationEvent(int eventId) {
        this.eventId = eventId;
    }

    public LocationEvent(int eventId, LocationInfo locationInfo) {
        this.eventId = eventId;
        this.locationInfo = locationInfo;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public LocationInfo getLocationInfo() {
        return locationInfo;
    }

    public void setLocationInfo(LocationInfo locationInfo) {
        this.locationInfo = locationInfo;
    }
}
