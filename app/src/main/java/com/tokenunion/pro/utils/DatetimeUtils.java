package com.tokenunion.pro.utils;

import com.anypocket.pro.R;
import com.tokenunion.pro.app.TUApplication;

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
        /*
        if (monthC == 6) {
            return TUApplication.INSTANCE.getString(R.string.mine_message_half_years_ago); // 半年前
        }
        if (monthC == 12) {
            return TUApplication.INSTANCE.getString(R.string.mine_message_one_years_ago);//"一年前";
        }
        if (monthC > 12) {
            return timeStamp2Date(timeStamp, "yyyy-MM-dd HH:mm:ss");
        }
        if(monthC >= 1){
            return Integer.parseInt(monthC+"") + TUApplication.INSTANCE.getString(R.string.mine_message_months_ago);//"月前";
        }
        else if(weekC >= 1){
            return Integer.parseInt(weekC+"") + TUApplication.INSTANCE.getString(R.string.mine_message_weeks_ago);//"周前";
        }
        else if(dayC >= 1){
            return Integer.parseInt(dayC+"") + TUApplication.INSTANCE.getString(R.string.mine_message_days_ago);//"天前";
        }
        else if(hourC >= 1){
            return Integer.parseInt(hourC+"") + TUApplication.INSTANCE.getString(R.string.mine_message_hours_ago);//"小时前";
        }
        else if(minC >= 1){
            return Integer.parseInt(minC+"") + TUApplication.INSTANCE.getString(R.string.mine_message_minintes_ago);//"分钟前";
        }else {
            return TUApplication.INSTANCE.getString(R.string.mine_message_just_now);//"刚刚";
        }*/

        // 如果小于4小时，则显示"刚刚"；否则直接显示：年月日时分秒
        if(hourC >= 4){
            return "";
        }else{
            return TUApplication.INSTANCE.getString(R.string.mine_message_just_now);
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

//    public static String getLocalTime(String timeUTC_7){
//        SimpleDateFormat dff = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        dff.setTimeZone(TimeZone.getTimeZone("GMT+08"));
//        String ee = dff.format(new Date());
//    }

}
