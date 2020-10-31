package com.inft.awm.response;

import java.util.List;

/**
 * Get daily salary
 *
 * @author Yao Shi
 * @version 1.0
 * @date 30/10/2020 11:47 pm
 */
public class ResponseDailySalary {
    private String date;
    private double work_hours;
    private double salary;
    private Float payment_rate;

    private String on_time;
    private String off_time;

    public ResponseDailySalary(String date, double work_hours, double salary, Float payment_rate, String on_time,String off_time) {
        this.date = date;
        this.work_hours = work_hours;
        this.salary = salary;
        this.payment_rate = payment_rate;
        this.on_time = on_time;
        this.off_time = off_time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getWork_hours() {
        return work_hours;
    }

    public void setWork_hours(double work_hours) {
        this.work_hours = work_hours;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public Float getPayment_rate() {
        return payment_rate;
    }

    public void setPayment_rate(Float payment_rate) {
        this.payment_rate = payment_rate;
    }

    public String getOn_time() {
        return on_time;
    }

    public void setOn_time(String on_time) {
        this.on_time = on_time;
    }

    public String getOff_time() {
        return off_time;
    }

    public void setOff_time(String off_time) {
        this.off_time = off_time;
    }
}
