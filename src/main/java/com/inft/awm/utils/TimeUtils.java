package com.inft.awm.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
}
