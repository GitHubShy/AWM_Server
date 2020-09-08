package com.inft.awm.utils;

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

    public static String getCurrentTime() {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        String dateTime = df.format(new Date());
        System.out.println(dateTime);
        return dateTime;
    }
}
