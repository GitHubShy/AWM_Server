package com.inft.awm.domain;

import javax.persistence.*;

@Entity
@Table
public class Aircraft {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    protected String type;

    protected String aircraft_pic;

    /**
     * MSN=Manufacturing Serial number 制造序列号，制造商对其所有生产线生产的飞机数量的统计，不区分机型，
     *    表示该架飞机在厂家所生产全部飞机中的排序号。比如：波音公司制造序列号为38388的飞机，代表波音生产的第38388架飞机。
     */
    protected String registration;

    /**
     * Registration number注册号，此编号由飞机持有国的官方制定，开头的一、两位的字母由国际航空组织给定，代表该航空器注册的国籍。
     *    一个注册号对应一架飞机，没有重号，必须符合国际民用航空公约的规定，一般在机身尾部和两侧机翼，少数在垂直尾翼。飞机注册号包括国籍标志和登记标志。比如：我国国内一般是B-XXXX(四位数）
     */
    protected String serial;

    protected Float total_flight_time;

    protected Integer maintenance_cycle;

    protected String last_modify_time;

    protected String next_modify_time;

    protected Integer customer_id;

    /**
     *0:Flighting  1:Maintaining
     */
    protected Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAircraft_pic() {
        return aircraft_pic;
    }

    public void setAircraft_pic(String aircraft_pic) {
        this.aircraft_pic = aircraft_pic;
    }

    public String getRegistration() {
        return registration;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public Float getTotal_flight_time() {
        return total_flight_time;
    }

    public void setTotal_flight_time(Float total_flight_time) {
        this.total_flight_time = total_flight_time;
    }

    public Integer getMaintenance_cycle() {
        return maintenance_cycle;
    }

    public void setMaintenance_cycle(Integer maintenance_cycle) {
        this.maintenance_cycle = maintenance_cycle;
    }

    public String getLast_modify_time() {
        return last_modify_time;
    }

    public void setLast_modify_time(String last_modify_time) {
        this.last_modify_time = last_modify_time;
    }

    public String getNext_modify_time() {
        return next_modify_time;
    }

    public void setNext_modify_time(String next_modify_time) {
        this.next_modify_time = next_modify_time;
    }

    public Integer getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(Integer customer_id) {
        this.customer_id = customer_id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
