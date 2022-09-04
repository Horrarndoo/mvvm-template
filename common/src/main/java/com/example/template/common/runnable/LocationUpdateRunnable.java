package com.example.template.common.runnable;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.example.template.common.bean.LocationInfo;
import com.example.template.common.global.CommonApplication;
import com.example.template.support.utils.AppUtils;
import com.example.template.support.utils.LogUtils;

import java.text.DecimalFormat;

/**
 * Created by Horrarndoo on 2022/9/1.
 * <p>
 * 本机位置信息更新任务
 * 2s或位置变化超过10m更新一次，高程信息10s定期查询一次
 */
public class LocationUpdateRunnable implements Runnable {
    private final static int UPDATE_INTEVAL = 10000; // ms
    private volatile boolean isStop = false;
    private final LocationManager mLocationManager;
    private OnLocationChangeListener mOnLocationChangeListener;

    public LocationUpdateRunnable(OnLocationChangeListener listener) {
        mLocationManager =
                (LocationManager) CommonApplication.getContext().getSystemService(Context.LOCATION_SERVICE);
        mOnLocationChangeListener = listener;
    }

    @Override
    public void run() {
        while (!isStop) {
            try {
                AppUtils.runOnUIThread(new Runnable() {
                    @SuppressLint("MissingPermission")
                    @Override
                    public void run() {
                        Criteria criteria = new Criteria();
                        String provider = mLocationManager.getBestProvider(criteria, false);
                        if (provider != null) {
                            mLocationManager.getLastKnownLocation(provider);
                            mLocationManager.requestLocationUpdates(provider, 2000, 10,
                                    mLocationListener);
                        }
                    }
                });
                Thread.sleep(UPDATE_INTEVAL);
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    /**
     * 停止runnable
     */
    @SuppressLint("MissingPermission")
    public void setStop() {
        this.isStop = true;
        mOnLocationChangeListener = null;
        mLocationManager.removeUpdates(mLocationListener);
    }

    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            if (location == null)
                return;

            //将经纬度保留到小数点后五位
            DecimalFormat df = new DecimalFormat("0.#####");
            //高程保留到小数点后一位
            DecimalFormat df2 = new DecimalFormat("0.#");

            String longitude = df.format(location.getLongitude());
            String latitude = df.format(location.getLatitude());
            String altitude = df2.format(location.getAltitude());

            LocationInfo locationInfo = new LocationInfo();
            locationInfo.longitude = Double.parseDouble(longitude);
            locationInfo.latitude = Double.parseDouble(latitude);
            locationInfo.altitude = Double.parseDouble(altitude);
            //精度强转为整型
            locationInfo.accuracy = (int) location.getAccuracy();

            if (locationInfo.isValid()) {
                //LogUtils.d("LocationChanged location = " + locationInfo.toString());
                if (mOnLocationChangeListener != null) {
                    mOnLocationChangeListener.onChanged(locationInfo);
                }
            } else {
                LogUtils.e("location info invalid. ---> " + locationInfo.toString());
            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onProviderDisabled(String provider) {
        }
    };

    /**
     * 位置变化监听
     */
    public interface OnLocationChangeListener {
        void onChanged(LocationInfo locationInfo);
    }
}
