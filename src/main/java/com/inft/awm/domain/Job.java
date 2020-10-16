package com.inft.awm.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "The aircraft_id cannot be empty")
    private Integer aircraft_id;

    /**
     * Only allow manager
     */
    @NotNull(message = "The employee_id cannot be empty")
    private Integer employee_id;

    private String description;

    @NotBlank(message = "The start_time cannot be empty")
    private String start_time;

    @NotBlank(message = "The due_time cannot be empty")
    private String due_time;

    @NotNull(message = "You must to choose a job template")
    private Integer template_id;


    private String  end_time;

    private Double  planned_cost_time;

    private Double  actual_cost_time;

    @Transient
    private String employee_name;

    @Transient
    private String template_title;

    /* 0:created 1:started 2: approach due 3:over due 4：Need confirm finished5:finished*/
    private Integer status;

    public String getTemplate_title() {
        return template_title;
    }

    public void setTemplate_title(String template_title) {
        this.template_title = template_title;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAircraft_id() {
        return aircraft_id;
    }

    public void setAircraft_id(Integer aircraft_id) {
        this.aircraft_id = aircraft_id;
    }

    public Integer getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(Integer employee_id) {
        this.employee_id = employee_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getDue_time() {
        return due_time;
    }

    public void setDue_time(String due_time) {
        this.due_time = due_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public Double getPlanned_cost_time() {
        return planned_cost_time;
    }

    public Integer getTemplate_id() {
        return template_id;
    }

    public void setTemplate_id(Integer template_id) {
        this.template_id = template_id;
    }

    public void setPlanned_cost_time(Double planned_cost_time) {
        this.planned_cost_time = planned_cost_time;
    }

    public Double getActual_cost_time() {
        return actual_cost_time;
    }

    public void setActual_cost_time(Double actual_cost_time) {
        this.actual_cost_time = actual_cost_time;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getEmployee_name() {
        return employee_name;
    }

    public void setEmployee_name(String employee_name) {
        this.employee_name = employee_name;
    }
}
