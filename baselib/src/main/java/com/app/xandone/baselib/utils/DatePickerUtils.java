package com.app.xandone.baselib.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * author: Admin
 * created on: 2019/8/5 15:19
 * description:
 */
public class DatePickerUtils {

    /**
     * 时间选择器
     *
     * @param onTimeSetListener
     */
    public static void showDate(Context context, final OnTimeSetListener onTimeSetListener) {
        showDate(context, System.currentTimeMillis(), onTimeSetListener);
    }

    /**
     * 当前日期为最大日期
     *
     * @param context
     * @param onTimeSetListener
     */
    public static void showDateWithNoHour(Context context, final OnTimeSetListener onTimeSetListener) {
        showDateWithNoHour(context, System.currentTimeMillis(), onTimeSetListener);
    }

    public static void showDateOnlyYear(Context context, final OnTimeSetListener onTimeSetListener) {
        showDateOnlyYear(context, System.currentTimeMillis(), onTimeSetListener);
    }

    /**
     * 时间选择器
     *
     * @param onTimeSetListener
     */
    public static void showDate(Context context, long selectedMillis, final OnTimeSetListener onTimeSetListener) {
        showDate(context, "", selectedMillis, onTimeSetListener);
    }

    /**
     * 时间选择器
     *
     * @param onTimeSetListener
     */
    public static void showDate(Context context, String title, long selectedMillis, final OnTimeSetListener onTimeSetListener) {
        Calendar selectedDate = Calendar.getInstance(Locale.CHINA);
        selectedDate.setTimeInMillis(selectedMillis);
        Calendar startDate = Calendar.getInstance();
        startDate.setTimeInMillis(System.currentTimeMillis());
        Calendar endDate = Calendar.getInstance();
        endDate.set(startDate.get(Calendar.YEAR) + 70, startDate.get(Calendar.MONTH), startDate.get(Calendar.DAY_OF_MONTH));
        //时间选择器
        new TimePickerBuilder(context, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                Calendar d = Calendar.getInstance();
                d.setTime(date);
                int year = d.get(Calendar.YEAR);
                int month = d.get(Calendar.MONTH);
                int day = d.get(Calendar.DAY_OF_MONTH);
//                int hour = d.get(Calendar.HOUR_OF_DAY);
                onTimeSetListener.onTimeSet(year + "-" + (month + 1) + "-" + day
//                        + " " + hour + ":00:00"
                );
            }
        })
//                年月日时分秒 的显示与否，不设置则默认全部显示
                .setType(new boolean[]{true, true, true, false, false, false})
                .setLabel("年", "月", "日", "时", "", "")
                .setDividerColor(Color.parseColor("#e3e3e3"))
                .setContentTextSize(20)
                .setTitleText(title)
                .setDate(selectedDate)
                //控制时间范围(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
//                .setRangDate(startDate, endDate)
                .build()
                .show();

    }

    public static void showDateWithNoHour(Context context, long selectedMillis, final OnTimeSetListener onTimeSetListener) {
        //控制时间范围(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
        Calendar selectedDate = Calendar.getInstance(Locale.CHINA);
        selectedDate.setTimeInMillis(selectedMillis);
        Calendar endDate = Calendar.getInstance();
        endDate.setTimeInMillis(System.currentTimeMillis());
        Calendar startDate = Calendar.getInstance();
        startDate.set(endDate.get(Calendar.YEAR) - 70, endDate.get(Calendar.MONTH), endDate.get(Calendar.DAY_OF_MONTH));
        //时间选择器
        new TimePickerBuilder(context, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                onTimeSetListener.onTimeSet(DateUtils.date2String(date, DateUtils.NO_HOUR_FORMAT));
            }
        })
                //年月日时分秒 的显示与否，不设置则默认全部显示
                .setType(new boolean[]{true, true, true, false, false, false})
                .setLabel("年", "月", "日", "", "", "")
                .setDividerColor(Color.parseColor("#e3e3e3"))
                .setContentTextSize(20)
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .build()
                .show();
    }

    public static void showDateOnlyYear(Context context, long selectedMillis, final OnTimeSetListener onTimeSetListener) {
        //控制时间范围(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
        Calendar selectedDate = Calendar.getInstance(Locale.CHINA);
        selectedDate.setTimeInMillis(selectedMillis);
        Calendar endDate = Calendar.getInstance();
        endDate.setTimeInMillis(System.currentTimeMillis());
        Calendar startDate = Calendar.getInstance();
        startDate.set(endDate.get(Calendar.YEAR) - 70, endDate.get(Calendar.MONTH), endDate.get(Calendar.DAY_OF_MONTH));
        //时间选择器
        new TimePickerBuilder(context, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                onTimeSetListener.onTimeSet(DateUtils.date2String(date, DateUtils.ONLY_YEAR_FORMAT));
            }
        })
                //年月日时分秒 的显示与否，不设置则默认全部显示
                .setType(new boolean[]{true, false, false, false, false, false})
                .setLabel("年", "", "", "", "", "")
                .setDividerColor(Color.parseColor("#e3e3e3"))
                .setContentTextSize(20)
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .build()
                .show();
    }

    public interface OnTimeSetListener {
        void onTimeSet(String time);
    }

    @SuppressLint("SimpleDateFormat")
    public static String getStringTime(long time) {
        return new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(time);
    }

    public static long getLongTime(String time) {
        if (!TextUtils.isEmpty(time)) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            try {
                Date date = format.parse(time);
                return date.getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return System.currentTimeMillis();
    }

}
