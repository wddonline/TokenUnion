package com.iconiz.tokenunion.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DatetimeUtils {

    /**
     * 时间戳转模糊时间
     * @param timeStamp 时间戳
     * @return
     */
    public static String getFuzzyTime(long timeStamp) {
        final long minute = 1000 * 60;
        final long hour = minute * 60;
        final long day = hour * 24;
        final long month = day * 30;

        long now = new Date().getTime();
        long diffValue = now - timeStamp;
        long monthC = diffValue / month;
        long weekC = diffValue / (7 * day);
        long dayC = diffValue / day;
        long hourC = diffValue / hour;
        long minC = diffValue / minute;
        if (monthC == 6) {
            return "半年前";
        }
        if (monthC == 12) {
            return "一年前";
        }
        if (monthC > 12) {
            return timeStamp2Date(timeStamp, "yyyy-MM-dd HH:mm:ss");
        }
        if(monthC >= 1){
            return Integer.parseInt(monthC+"") + "月前";
        }
        else if(weekC >= 1){
            return Integer.parseInt(weekC+"") + "周前";
        }
        else if(dayC >= 1){
            return Integer.parseInt(dayC+"") +"天前";
        }
        else if(hourC >= 1){
            return Integer.parseInt(hourC+"") +"小时前";
        }
        else if(minC >= 1){
            return Integer.parseInt(minC+"") +"分钟前";
        }else {
            return "刚刚";
        }
    }

    public static String timeStamp2Date(long timeStamp, String format) {
        if(format == null || format.isEmpty()){
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(timeStamp));
    }

    /**
     * 日期格式字符串转换成时间戳
     * @param date 字符串日期
     * @param format 如：yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static long date2TimeStamp(String date, String format){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.parse(date).getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

}
