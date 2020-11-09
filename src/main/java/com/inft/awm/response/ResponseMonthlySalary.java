package com.inft.awm.response;

import java.util.List;

/**
 * Get monthly salary
 *
 * @author Yao Shi
 * @version 1.0
 * @date 30/10/2020 10:47 pm
 */
public class ResponseMonthlySalary {
    //month
    private String date;
    //total worked hours in this month
    private double work_hours;
    //total salary in this month
    private double salary;
    //payment rate
    private Float payment_rate;
    //Each day salary
    private List<ResponseDailySalary> daily_salary;

    public ResponseMonthlySalary(String date, double work_hours, double salary, Float payment_rate, List<ResponseDailySalary> daily_salary) {
        this.date = date;
        this.work_hours = work_hours;
        this.salary = salary;
        this.payment_rate = payment_rate;
        this.daily_salary = daily_salary;
    }

    public String getDate() {
        return date;
    }

    public double getWork_hours() {
        return work_hours;
    }

    public double getSalary() {
        return salary;
    }

    public Float getPayment_rate() {
        return payment_rate;
    }

    public List<ResponseDailySalary> getDaily_salary() {
        return daily_salary;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setWork_hours(double work_hours) {
        this.work_hours = work_hours;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public void setPayment_rate(Float payment_rate) {
        this.payment_rate = payment_rate;
    }

    public void setDaily_salary(List<ResponseDailySalary> daily_salary) {
        this.daily_salary = daily_salary;
    }
}
