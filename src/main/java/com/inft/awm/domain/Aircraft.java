package com.inft.awm.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * mapping of aircraft table
 *
 * @author Yao Shi
 * @version 1.0
 * @date 30/10/2020 11:47 pm
 */
@Entity
@Table
public class Aircraft {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    /**
     *  For example A380
     */
    @NotBlank(message = "The type cannot be empty")
    protected String type;

    /**
     * MSN=Manufacturing Serial number
     * Manufacturing serial number, manufacturer's statistics of the number of aircraft produced by all its production lines, regardless of aircraft type,
     * It indicates the sequence number of the aircraft in all the aircraft produced by the manufacturer. For example, Boeing company manufactures the aircraft with serial number 38388, which represents the 38388 aircraft produced by Boeing.
     */
    @NotBlank(message = "The registration cannot be empty")
    protected String registration;

    /**
     * Registration number
     * The registration number, which is formulated by the official of the aircraft holding country, and the first one or two letters are given by the international aviation organization to represent the registered nationality of the aircraft.
     *  A registration number corresponds to an aircraft, and there is no duplicate number. It must comply with the provisions of the International Civil Aviation Convention. Generally, it is at the tail of the fuselage and the wings on both sides, and a few on the vertical tail. The aircraft registration number includes nationality mark and registration mark. For example: China's domestic general b-xxx (four digit)
     */
    @NotBlank(message = "The serial cannot be empty")
    protected String serial;

    protected Float total_flight_time;

    @NotNull(message = "The maintenance_cycle cannot be empty")
    protected Integer maintenance_cycle;

    /**
     * 2016-10-22
     */
    @NotBlank(message = "The last_modify_time cannot be empty")
    protected String last_modify_time;

    @NotNull(message = "The customer_id cannot be empty")
    protected Integer customer_id;

    /**
     * 0:Servicing  1:Need maintaining 2:Maintaining 99:need staff to confirm register information
     */
    protected Integer status;

    /**
     * 2020-10-22
     */
    protected String next_modify_time;

    protected String aircraft_pic;

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
