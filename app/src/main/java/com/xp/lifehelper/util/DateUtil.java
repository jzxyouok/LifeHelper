package com.xp.lifehelper.util;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by xp on 2017/5/23.
 */
public class DateUtil {
    /**
     * 将long类date转换为String类型
     * @param date date
     * @return String date
     */
    public static String formatDateFromLong(long date) {
        String sDate;
        Date d = new Date(date);
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        sDate = format.format(d);

        return sDate;
    }
    public static String format(String data){
        if(getToday().equals(data)){
            return "今日新闻";
        }else{
            StringBuilder sb=new StringBuilder();
            sb.append(data.substring(4,6)).append("月");
            sb.append(data.substring(6,8)).append("日 ");
            sb.append(getWeek(data));
            return sb.toString();
        }
    }
    public static String getToday(){
        Calendar can= Calendar.getInstance();
        int year=can.get(Calendar.YEAR);
        int month=can.get(Calendar.MONTH)+1;
        int day=can.get(Calendar.DAY_OF_MONTH);
        return year+formatString(month)+formatString(day);
    }
    public static String formatString(int source){
       return String.format("%02d",source);
    }

    /**
     * 获取星期几
     * @return
     */
    public static String getWeek(String data){
        Calendar can= Calendar.getInstance();
        int year=Integer.parseInt(data.substring(0,4));
        int month=Integer.parseInt(data.substring(4,6));
        int day=Integer.parseInt(data.substring(6,8));
        can.set(year,month-1,day);//month与实际月份相差1
        switch (can.get(Calendar.DAY_OF_WEEK)){
            case 1:return "星期日";
            case 2:return "星期一";
            case 3:return "星期二";
            case 4:return "星期三";
            case 5:return "星期四";
            case 6:return "星期五";
            case 7:return "星期六";
        }
        Log.i("TAG", "getWeek: "+year+","+month+","+day+","+can.get(Calendar.DAY_OF_WEEK));
        return "未知";
    }
}
