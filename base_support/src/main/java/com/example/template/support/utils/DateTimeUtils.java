package com.example.template.support.utils;

import android.annotation.SuppressLint;

import com.example.template.support.base.model.DateTimeInfo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Horrarndoo on 2018/12/6.
 * <p>
 */
public class DateTimeUtils {
    /**
     * yyyy-MM-dd HH:mm:ss字符串
     */
    public static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * yyyy-MM-dd字符串
     */
    public static final String DEFAULT_FORMAT_DATE = "yyyy-MM-dd";

    /**
     * HH:mm:ss字符串
     */
    public static final String DEFAULT_FORMAT_TIME = "HH:mm:ss";

    /**
     * 获取格式化后的当前时间
     *
     * @return 当前时间 "yyyy/MM/dd HH:mm"
     */
    public static String getFormatsCurrentTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }

    /**
     * 获取格式化后的当前时分秒
     *
     * @return 当前时间时分秒 "HH:mm:ss"
     */
    public static String getFormatsHourMinSec(long time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date(time);
        return simpleDateFormat.format(date);
    }

    /**
     * 获取格式化后的时间
     *
     * @return 格式化后的时间 "yyyy/MM/dd HH:mm"
     */
    public static String getFormatsTime(int year, int month, int day, int hour, int min) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        Date date = new Date(year - 1900, month - 1, day, hour, min);
        return simpleDateFormat.format(date);
    }

    /**
     * 获取格式化后的时间（无年份）
     *
     * @return 格式化后的时间 "MM/dd HH:mm"
     */
    public static String getFormatsTimeNoYear(int year, int month, int day, int hour, int min) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd HH:mm");
        Date date = new Date(year - 1900, month - 1, day, hour, min);
        return simpleDateFormat.format(date);
    }

    /**
     * 将yyyy/MM/dd HH:mm格式化成整型数据，异常时返回-1
     *
     * @param times yyyy/MM/dd HH:mm
     * @return 整型数据
     */
    public static long getFormatsTimeLong(String times) {
        try {
            String s = times.replaceAll("/", "");
            String s1 = s.replaceAll(":", "");
            String s2 = s1.replaceAll(" ", "");
            String s3 = s2.replaceAll("年", "");
            String s4 = s3.replaceAll("月", "");
            String s5 = s4.replaceAll("日", "");
            String s6 = s5.replaceAll("时", "");
            String s7 = s6.replaceAll("分", "");
            String s8 = s7.replaceAll("秒", "");
            return Long.valueOf(s8);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 将yyy/MM/dd HH:mm格式化成时间数据
     *
     * @param times yyy/MM/dd HH:mm
     * @return 时间数据
     */
    public static DateTimeInfo formatDataTime(String times) {
        DateTimeInfo dateTimeInfo = new DateTimeInfo();
        String s = times.replaceAll("/", "");
        String s1 = s.replaceAll(":", "");
        String s2 = s1.replaceAll(" ", "");
        dateTimeInfo.year = Integer.parseInt(s2.substring(0, 4));
        dateTimeInfo.month = Integer.parseInt(s2.substring(4, 6));
        dateTimeInfo.day = Integer.parseInt(s2.substring(6, 8));
        dateTimeInfo.hour = Integer.parseInt(s2.substring(8, 10));
        dateTimeInfo.min = Integer.parseInt(s2.substring(10, 12));
        return dateTimeInfo;
    }

    /**
     * yyyy-MM-dd HH:mm:ss格式
     */
    public static final ThreadLocal<SimpleDateFormat> defaultDateTimeFormat = new
            ThreadLocal<SimpleDateFormat>() {

                @SuppressLint("SimpleDateFormat")
                @Override
                protected SimpleDateFormat initialValue() {
                    return new SimpleDateFormat(DEFAULT_DATE_TIME_FORMAT);
                }

            };

    /**
     * yyyy-MM-dd格式
     */
    public static final ThreadLocal<SimpleDateFormat> defaultDateFormat = new
            ThreadLocal<SimpleDateFormat>() {

                @SuppressLint("SimpleDateFormat")
                @Override
                protected SimpleDateFormat initialValue() {
                    return new SimpleDateFormat(DEFAULT_FORMAT_DATE);
                }

            };

    /**
     * HH:mm:ss格式
     */
    public static final ThreadLocal<SimpleDateFormat> defaultTimeFormat = new
            ThreadLocal<SimpleDateFormat>() {

                @SuppressLint("SimpleDateFormat")
                @Override
                protected SimpleDateFormat initialValue() {
                    return new SimpleDateFormat(DEFAULT_FORMAT_TIME);
                }

            };

    /**
     * 将long时间转成yyyy-MM-dd HH:mm:ss字符串<br>
     *
     * @param timeInMillis 时间long值
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String getDateTimeFromMillis(long timeInMillis) {
        return getDateTimeFormat(new Date(timeInMillis));
    }

    /**
     * 将long时间转成yyyy-MM-dd字符串<br>
     *
     * @param timeInMillis
     * @return yyyy-MM-dd
     */
    public static String getDateFromMillis(long timeInMillis) {
        return getDateFormat(new Date(timeInMillis));
    }

    /**
     * 将date转成yyyy-MM-dd HH:mm:ss字符串
     * <br>
     *
     * @param date Date对象
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String getDateTimeFormat(Date date) {
        return dateSimpleFormat(date, defaultDateTimeFormat.get());
    }

    /**
     * 将年月日的int转成yyyy-MM-dd的字符串
     *
     * @param year  年
     * @param month 月 1-12
     * @param day   日
     *              注：月表示Calendar的月，比实际小1
     *              对输入项未做判断
     */
    public static String getDateFormat(int year, int month, int day) {
        return getDateFormat(getDate(year, month, day));
    }

    /**
     * 将date转成yyyy-MM-dd字符串<br>
     *
     * @param date Date对象
     * @return yyyy-MM-dd
     */
    public static String getDateFormat(Date date) {
        return dateSimpleFormat(date, defaultDateFormat.get());
    }

    /**
     * 获得HH:mm:ss的时间
     *
     * @param date
     * @return
     */
    public static String getTimeFormat(Date date) {
        return dateSimpleFormat(date, defaultTimeFormat.get());
    }

    /**
     * 格式化日期显示格式
     *
     * @param sdate  原始日期格式 "yyyy-MM-dd"
     * @param format 格式化后日期格式
     * @return 格式化后的日期显示
     */
    public static String dateFormat(String sdate, String format) {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        java.sql.Date date = java.sql.Date.valueOf(sdate);
        return dateSimpleFormat(date, formatter);
    }

    /**
     * 格式化日期显示格式
     *
     * @param date   Date对象
     * @param format 格式化后日期格式
     * @return 格式化后的日期显示
     */
    public static String dateFormat(Date date, String format) {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return dateSimpleFormat(date, formatter);
    }

    /**
     * 将date转成字符串
     *
     * @param date   Date
     * @param format SimpleDateFormat
     *               <br>
     *               注： SimpleDateFormat为空时，采用默认的yyyy-MM-dd HH:mm:ss格式
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String dateSimpleFormat(Date date, SimpleDateFormat format) {
        if (format == null)
            format = defaultDateTimeFormat.get();
        return (date == null ? "" : format.format(date));
    }

    /**
     * 将"yyyy-MM-dd HH:mm:ss" 格式的字符串转成Date
     *
     * @param strDate 时间字符串
     * @return Date
     */
    public static Date getDateByDateTimeFormat(String strDate) {
        return getDateByFormat(strDate, defaultDateTimeFormat.get());
    }

    /**
     * 将"yyyy-MM-dd" 格式的字符串转成Date
     *
     * @param strDate
     * @return Date
     */
    public static Date getDateByDateFormat(String strDate) {
        return getDateByFormat(strDate, defaultDateFormat.get());
    }

    /**
     * 将指定格式的时间字符串转成Date对象
     *
     * @param strDate 时间字符串
     * @param format  格式化字符串
     * @return Date
     */
    @SuppressLint("SimpleDateFormat")
    public static Date getDateByFormat(String strDate, String format) {
        return getDateByFormat(strDate, new SimpleDateFormat(format));
    }

    /**
     * 将String字符串按照一定格式转成Date<br>
     * 注： SimpleDateFormat为空时，采用默认的yyyy-MM-dd HH:mm:ss格式
     *
     * @param strDate 时间字符串
     * @param format  SimpleDateFormat对象
     */
    private static Date getDateByFormat(String strDate, SimpleDateFormat format) {
        if (format == null)
            format = defaultDateTimeFormat.get();
        try {
            return format.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将年月日的int转成date
     *
     * @param year  年
     * @param month 月 1-12
     * @param day   日
     *              注：月表示Calendar的月，比实际小1
     */
    public static Date getDate(int year, int month, int day) {
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.set(year, month - 1, day);
        return mCalendar.getTime();
    }

    /**
     * 求两个日期相差天数
     *
     * @param strat 起始日期，格式yyyy-MM-dd
     * @param end   终止日期，格式yyyy-MM-dd
     * @return 两个日期相差天数
     */
    public static long getIntervalDays(String strat, String end) {
        return ((java.sql.Date.valueOf(end)).getTime() - (java.sql.Date
                .valueOf(strat)).getTime()) / (3600 * 24 * 1000);
    }

    /**
     * 获得当前年份
     *
     * @return year(int)
     */
    public static int getCurrentYear() {
        Calendar mCalendar = Calendar.getInstance();
        return mCalendar.get(Calendar.YEAR);
    }

    /**
     * 获得当前月份
     *
     * @return month(int) 1-12
     */
    public static int getCurrentMonth() {
        Calendar mCalendar = Calendar.getInstance();
        return mCalendar.get(Calendar.MONTH) + 1;
    }

    /**
     * 获得当月几号
     *
     * @return day(int)
     */
    public static int getDayOfMonth() {
        Calendar mCalendar = Calendar.getInstance();
        return mCalendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获得今天的日期(格式：yyyy-MM-dd)
     *
     * @return yyyy-MM-dd
     */
    public static String getToday() {
        Calendar mCalendar = Calendar.getInstance();
        return getDateFormat(mCalendar.getTime());
    }

    /**
     * 获得昨天的日期(格式：yyyy-MM-dd)
     *
     * @return yyyy-MM-dd
     */
    public static String getYesterday() {
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.add(Calendar.DATE, -1);
        return getDateFormat(mCalendar.getTime());
    }

    /**
     * 获得前天的日期(格式：yyyy-MM-dd)
     *
     * @return yyyy-MM-dd
     */
    public static String getBeforeYesterday() {
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.add(Calendar.DATE, -2);
        return getDateFormat(mCalendar.getTime());
    }

    /**
     * 获得几天之前或者几天之后的日期
     *
     * @param diff 差值：正的往后推，负的往前推
     * @return
     */
    public static String getOtherDay(int diff) {
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.add(Calendar.DATE, diff);
        return getDateFormat(mCalendar.getTime());
    }

    /**
     * 取得给定日期加上一定天数后的日期对象.
     *
     * @param
     * @param amount 需要添加的天数，如果是向前的天数，使用负数就可以.
     * @return Date 加上一定天数以后的Date对象.
     */
    public static String getCalcDateFormat(String sDate, int amount) {
        Date date = getCalcDate(getDateByDateFormat(sDate), amount);
        return getDateFormat(date);
    }

    /**
     * 取得给定日期加上一定天数后的日期对象.
     *
     * @param date   给定的日期对象
     * @param amount 需要添加的天数，如果是向前的天数，使用负数就可以.
     * @return Date 加上一定天数以后的Date对象.
     */
    public static Date getCalcDate(Date date, int amount) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, amount);
        return cal.getTime();
    }

    /**
     * 获得一个计算十分秒之后的日期对象
     *
     * @param date
     * @param hOffset 时偏移量，可为负
     * @param mOffset 分偏移量，可为负
     * @param sOffset 秒偏移量，可为负
     * @return
     */
    public static Date getCalcTime(Date date, int hOffset, int mOffset, int sOffset) {
        Calendar cal = Calendar.getInstance();
        if (date != null)
            cal.setTime(date);
        cal.add(Calendar.HOUR_OF_DAY, hOffset);
        cal.add(Calendar.MINUTE, mOffset);
        cal.add(Calendar.SECOND, sOffset);
        return cal.getTime();
    }

    /**
     * 根据指定的年月日小时分秒，返回一个java.Util.Date对象。
     *
     * @param year      年
     * @param month     月 0-11
     * @param date      日
     * @param hourOfDay 小时 0-23
     * @param minute    分 0-59
     * @param second    秒 0-59
     * @return 一个Date对象
     */
    public static Date getDate(int year, int month, int date, int hourOfDay,
                               int minute, int second) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, date, hourOfDay, minute, second);
        return cal.getTime();
    }

    /**
     * 获得年月日数据
     *
     * @param sDate yyyy-MM-dd格式
     * @return arr[0]:年， arr[1]:月 0-11 , arr[2]日
     */
    public static int[] getYearMonthAndDayFrom(String sDate) {
        return getYearMonthAndDayFromDate(getDateByDateFormat(sDate));
    }

    /**
     * 获得年月日数据
     *
     * @return arr[0]:年， arr[1]:月 0-11 , arr[2]日
     */
    public static int[] getYearMonthAndDayFromDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int[] arr = new int[3];
        arr[0] = calendar.get(Calendar.YEAR);
        arr[1] = calendar.get(Calendar.MONTH);
        arr[2] = calendar.get(Calendar.DAY_OF_MONTH);
        return arr;
    }

    /**
     * 获取系统时间戳
     *
     * @return
     */
    public long getCurTimeLong() {
        long time = System.currentTimeMillis();
        return time;
    }

    /**
     * 获取当前时间
     *
     * @param pattern
     * @return
     */
    public static String getCurDate(String pattern) {
        SimpleDateFormat sDateFormat = new SimpleDateFormat(pattern);
        return sDateFormat.format(new Date());
    }

    /**
     * 时间戳转换成字符窜
     *
     * @param milSecond
     * @param pattern
     * @return
     */
    public static String getDateToString(long milSecond, String pattern) {
        Date date = new Date(milSecond);
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }

    /**
     * 将字符串转为时间戳
     *
     * @param dateString
     * @param pattern
     * @return
     */
    public static long getStringToDate(String dateString, String pattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        Date date = new Date();
        try {
            date = dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }
}
