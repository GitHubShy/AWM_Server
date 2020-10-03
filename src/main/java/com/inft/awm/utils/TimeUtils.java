package com.inft.awm.utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtils {

    public static void main(String[] args) {
        TimeUtils.getCurrentDateTime();
        TimeUtils.getCurrentDate();
        TimeUtils.getCurrentTime();
    }

    public static String getCurrentDateTime() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateTime = df.format(new Date());
        System.out.println(dateTime);
        return dateTime;
    }

    public static String getCurrentDate() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String dateTime = df.format(new Date());
        System.out.println(dateTime);
        return dateTime;
    }

    public static String getCurrentDate(String date) {
        String dateTime="2030-09-09";
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        if (StringUtils.isEmpty(date)) {
            dateTime = df.format(new Date());
        } else {
            try {
                dateTime =df.format(df.parse(date));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        System.out.println(dateTime);
        return dateTime;
    }

    public static String getCurrentTime() {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        String dateTime = df.format(new Date());
        System.out.println(dateTime);
        return dateTime;
    }

    public static long getDateDiff(String startTime, String endTime, String format){
        //按照传入的格式生成一个simpledateformate对象
        SimpleDateFormat sd = new SimpleDateFormat(format);
        long nd = 1000*24*60*60;//一天的毫秒数
        long nh = 1000*60*60;//一小时的毫秒数
        long nm = 1000*60;//一分钟的毫秒数
        long ns = 1000;//一秒钟的毫秒数
        long diff;
        //获得两个时间的毫秒时间差异
        try {
            diff = sd.parse(endTime).getTime() - sd.parse(startTime).getTime();
            long day = diff/nd;//计算差多少天
            long hour = diff%nd/nh;//计算差多少小时
            long min = diff%nd%nh/nm;//计算差多少分钟
            long sec = diff%nd%nh%nm/ns;//计算差多少秒//输出结果
            System.out.println("时间相差："+day+"天"+hour+"小时"+min+"分钟"+sec+"秒。");
            System.out.println("时间相差："+day+"天"+hour+"小时"+min+"分钟"+sec+"秒。");
            return min ;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static double getDateDiffHours(String startTime, String endTime, String format) {
        SimpleDateFormat sd = new SimpleDateFormat(format);
        long nd = 1000 * 24 * 60 * 60;//一天的毫秒数
        double nh = 1000 * 60 * 60.0;//一小时的毫秒数
        long diff;
        //获得两个时间的毫秒时间差异
        try {
            diff = sd.parse(endTime).getTime() - sd.parse(startTime).getTime();
            double hour = diff % nd / nh;//计算差多少小时
            NumberFormat nf=new DecimalFormat( "0.0 ");
            hour = Double.parseDouble(nf.format(hour));
            System.out.println("时间相差："+hour);
            return hour;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0.0;
    }


    public static String addDateHours(String startTime, Integer hours,String format){

        SimpleDateFormat sd = new SimpleDateFormat(format);

        Date date = null;
        try {
            date = sd.parse(startTime);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (date == null)
            return "";
        System.out.println("front:" + sd.format(date)); //显示输入的日期
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR, hours);// 24小时制
        date = cal.getTime();
        System.out.println("after:" + sd.format(date));  //显示更新后的日期
        return sd.format(date);

    }


}
