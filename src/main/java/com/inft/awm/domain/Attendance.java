package com.inft.awm.domain;

import javax.persistence.*;


@Entity
@Table
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer employee_id;

    private String date;

    private String on_time;

    private String off_time;

    private double work_hours;

    public Attendance() {
    }

    public Attendance(Integer employee_id, String date, String on_time, String off_time) {
        this.employee_id = employee_id;
        this.date = date;
        this.on_time = on_time;
        this.off_time = off_time;
    }

    public Attendance(Integer employee_id, String date, String on_time) {
        this.employee_id = employee_id;
        this.date = date;
        this.on_time = on_time;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(Integer employee_id) {
        this.employee_id = employee_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public double getWork_hours() {
        return work_hours;
    }

    public void setWork_hours(double work_hours) {
        this.work_hours = work_hours;
    }

}
