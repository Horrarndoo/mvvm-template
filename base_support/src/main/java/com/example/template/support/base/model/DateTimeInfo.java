package com.example.template.support.base.model;

/**
 * Created by Horrarndoo on 2019/3/20.
 * <p>
 */
public class DateTimeInfo {
    public int year;
    //月：1-12
    public int month;
    public int day;
    public int hour;
    public int min;

    @Override
    public String toString() {
        return "DateTimeInfo{" +
                "year=" + year +
                ", month=" + month +
                ", day=" + day +
                ", hour=" + hour +
                ", min=" + min +
                '}';
    }
}
