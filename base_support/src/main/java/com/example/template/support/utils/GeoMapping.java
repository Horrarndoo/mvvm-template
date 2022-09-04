package com.example.template.support.utils;

import com.dtis.map.common.DtisMath;
import com.dtis.map.projections.MercatorProjection;
import com.dtis.map.struct.PointLngLat;
import com.dtis.map.struct.PointPixel;

/**
 * Created by ligengyu on 2018/7/8.
 */

public class GeoMapping {

    //使用墨卡托映射，将经纬度坐标转换成平面坐标
    public static MercatorProjection projection = MercatorProjection.Instance;
    public static int level = 22;

    private GeoMapping(){
        throw new AssertionError();
    }


    //映射算法，有效距离为半径5KM的范围
    public static PointLngLat map(PointLngLat origCenter, PointLngLat projCenter, PointLngLat current) {
        PointLngLat proj;
        PointPixel enterPix = projection.FromLatLngToPixel(origCenter, level);
        //当前经纬度与当前中心点的物理距离
        double deltaXMeter, deltaYMeter;
        if(current.Lng() > origCenter.Lng()) {
            deltaXMeter = DtisMath.GetDistance(new PointLngLat(origCenter.Lng(), origCenter.Lat()),
                    new PointLngLat(current.Lng(), origCenter.Lat())) * 1000D;
        } else {
            deltaXMeter = -1 * DtisMath.GetDistance(new PointLngLat(origCenter.Lng(), origCenter.Lat()),
                    new PointLngLat(current.Lng(), origCenter.Lat())) * 1000D;
        }
        if(current.Lat() > origCenter.Lat() ) {
            deltaYMeter = -1 * DtisMath.GetDistance(new PointLngLat(origCenter.Lng(), origCenter.Lat()),
                    new PointLngLat(origCenter.Lng(), current.Lat())) * 1000D;
        } else {
            deltaYMeter = DtisMath.GetDistance(new PointLngLat(origCenter.Lng(), origCenter.Lat()),
                    new PointLngLat(origCenter.Lng(), current.Lat())) * 1000D;
        }
        //映射点墨卡托坐标
        PointPixel projCenterPix = projection.FromLatLngToPixel(projCenter, level);
        //映射点每像素对应的物理距离
        PointLngLat geo1 = projection.FromPixelToLatLng(projCenterPix.X(), projCenterPix.Y(), level);
        PointLngLat geo2 = projection.FromPixelToLatLng(projCenterPix.X() -1, projCenterPix.Y(), level);
        double disPerPix = DtisMath.GetDistance(geo1, geo2) * 1000.0D;
        //墨卡托坐标偏移量
        long deltaXPix = (long) (deltaXMeter/disPerPix);
        long deltaYPix = (long) (deltaYMeter/disPerPix);
        //映射后的墨卡托坐标
        PointPixel projPix = new PointPixel(projCenterPix.X() + deltaXPix, projCenterPix.Y() + deltaYPix);
        //获取经纬度坐标
        proj = projection.FromPixelToLatLng(projPix, level);
        return proj;
    }

}

