package com.example.template.common.bean;

import com.example.template.support.interfaces.IProtoBuf;

import java.util.Objects;

/**
 * Created by Horrarndoo on 2022/9/1.
 * <p>
 * 位置信息
 */
public class LocationInfo implements IProtoBuf<LocationInfo> {
    /**
     * 默认经度（此值表示无效）
     */
    public static final double DEFAULT_LONGITUDE = 181;
    /**
     * 默认纬度（此值表示无效）
     */
    public static final double DEFAULT_LATITUDE = 91;
    /**
     * 默认高程
     */
    public static final double DEFAULT_ALTITUDE = 0;
    /**
     * 默认精度
     */
    public static final int DEFAULT_LOCATION_ACCURACY = 0;

    //经度
    public double longitude = DEFAULT_LONGITUDE;
    //纬度
    public double latitude = DEFAULT_LATITUDE;
    //高程
    public double altitude = DEFAULT_ALTITUDE;
    //定位精度
    public int accuracy = DEFAULT_LOCATION_ACCURACY;

    @Override
    public String toString() {
        return "LocationInfo{" +
                "longitude=" + longitude +
                ", latitude=" + latitude +
                ", altitude=" + altitude +
                ", accuracy=" + accuracy +
                '}';
    }

    @Override
    public LocationInfo toBean(byte[] bytes) {
        try {
            ProtoBuf.LocationInfoData proto = ProtoBuf.LocationInfoData.parseFrom(bytes);
            this.longitude = proto.getLongitude();
            this.latitude = proto.getLatitude();
            this.altitude = proto.getAltitude();
            this.accuracy = proto.getAccuracy();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return this;
    }

    @Override
    public byte[] toBytes() {
        ProtoBuf.LocationInfoData proto;
        try {
            proto = ProtoBuf.LocationInfoData.newBuilder()
                    .setLongitude(this.longitude)
                    .setLatitude(this.latitude)
                    .setAltitude(this.altitude)
                    .setAccuracy(this.accuracy)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return proto.toByteArray();
    }

    /**
     * 位置信息是否有效
     *
     * @return 位置信息是否有效
     */
    public boolean isValid() {
        return (Math.abs(longitude) < 180 && Math.abs(latitude) < 90);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        LocationInfo that = (LocationInfo) o;
        return Double.compare(that.longitude, longitude) == 0 && Double.compare(that.latitude,
                latitude) == 0 && Double.compare(that.altitude, altitude) == 0 && accuracy == that.accuracy;
    }

    @Override
    public int hashCode() {
        return Objects.hash(longitude, latitude, altitude, accuracy);
    }
}
