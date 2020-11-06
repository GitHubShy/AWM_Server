package com.inft.awm.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
/**
 * mapping of Component table
 *
 * @author Yao Shi
 * @version 1.0
 * @date 30/10/2020 11:47 pm
 */
@Entity
@Table
public class Component {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 0:undefined 1:Engine 2:Wing 3:Fuselage 4:Avionics System 5:Landing Gear
     */
    @NotNull(message = "The type cannot be empty")
    private Integer type;

    /**
     * E0001,F0001,L0001
     * W0001,A0001
     */
    @NotBlank(message = "The registration cannot be empty")
    private String registration;

    /**
     * General Electric Company,Rolls-Royce Plc Company
     * Pratt & Whitney Group Company,The Airbus Company,The Boeing Company
     *
     */
    @NotBlank(message = "The manufacturer cannot be empty")
    private String provider;

    @NotNull(message = "The aircraft_id cannot be empty")
    private Integer aircraft_id;

    @NotNull(message = "The maintenance_cycle cannot be empty")
    private Integer maintenance_cycle;

    private String pic;

    private String last_modify_time;

    private String next_modify_time;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getRegistration() {
        return registration;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
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

    public Integer getAircraft_id() {
        return aircraft_id;
    }

    public void setAircraft_id(Integer aircraft_id) {
        this.aircraft_id = aircraft_id;
    }
}
