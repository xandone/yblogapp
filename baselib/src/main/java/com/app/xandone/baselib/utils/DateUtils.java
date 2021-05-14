package com.app.xandone.baselib.utils;

import android.annotation.SuppressLint;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * author: Admin
 * created on: 2019/8/5 15:19
 * description:
 */
public class DateUtils {

    public static final String DEFULT_DATE_FORMATE = "yyyy-MM-dd HH:mm:ss";
    public static final String DEFULT_DATE_ONLY_DAY = "yyyy-MM-dd";


    public static String getCurrentDate() {
        Date date = new Date();
        SimpleDateFormat sf = new SimpleDateFormat(DEFULT_DATE_FORMATE);
        return sf.format(date);
    }

    public static String getCurrentDate(String pattern) {
        Date date = new Date();
        SimpleDateFormat sf = new SimpleDateFormat(pattern);
        return sf.format(date);
    }

    /**
     * 将毫秒数转化为某格式的日期字符串
     *
     * @param longTime
     * @param formatType
     * @return
     */
    public static String long2DateStr(long longTime, String formatType) {
        Date date = new Date(longTime);
        String str = new SimpleDateFormat(formatType).format(date);
        return str;
    }

    /**
     * 将某种格式的日期字符串转化为毫秒数
     *
     * @param dateString
     * @param formatType
     * @return
     */
    public static long dateStr2Long(String dateString, String formatType) {
        SimpleDateFormat sf = new SimpleDateFormat(formatType);
        Date date = null;
        try {
            date = sf.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (date == null) {
            return 0;
        } else {
            long longTime = date.getTime();
            return longTime;
        }
    }

    @SuppressLint("SimpleDateFormat")
    private static final DateFormat DEFAULT_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @SuppressLint("SimpleDateFormat")
    public static final DateFormat NO_HOUR_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    @SuppressLint("SimpleDateFormat")
    public static final DateFormat ONLY_YEAR_FORMAT = new SimpleDateFormat("yyyy");

    public static String date2String(final Date date) {
        return date2String(date, DEFAULT_FORMAT);
    }

    public static String date2String(final Date date, final DateFormat format) {
        return format.format(date);
    }

    public static Date string2Date(final String time) {
        return string2Date(time, NO_HOUR_FORMAT);
    }

    public static Date string2Date(final String time, final DateFormat format) {
        try {
            return format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 去掉时分秒
     *
     * @param time
     * @return
     */
    public static String dateNoHour(String time) {
        if (SimpleUtils.isEmpty(time)) {
            return "";
        }
        return date2String(string2Date(time), NO_HOUR_FORMAT);
    }

}