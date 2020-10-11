package com.inft.awm.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "sub_task")
public class SubTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "The job id cannot be empty")
    private Integer job_id;

    @NotNull(message = "The sub_task_type_id cannot be empty")
    private Integer sub_task_type_id;

    @NotBlank(message = "The start_time cannot be empty")
    private String start_time;

    @NotBlank(message = "The due_time cannot be empty")
    private String due_time;

    private String  end_time;

    private Double  planned_cost_time;

    private Double  actual_cost_time;

    private Integer employee_id;

    /* 0:created 1:started 2: approach due 3:over due 4：Need confirm finished5:finished*/
    private Integer status;

    public SubTask( Integer job_id, Integer sub_task_type_id, String start_time, String due_time, Double planned_cost_time, Integer employee_id, Integer status) {
        this.job_id = job_id;
        this.sub_task_type_id = sub_task_type_id;
        this.start_time = start_time;
        this.due_time = due_time;
        this.planned_cost_time = planned_cost_time;
        this.employee_id = employee_id;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getJob_id() {
        return job_id;
    }

    public void setJob_id(Integer job_id) {
        this.job_id = job_id;
    }

    public Integer getSub_task_type_id() {
        return sub_task_type_id;
    }

    public void setSub_task_type_id(Integer sub_task_type_id) {
        this.sub_task_type_id = sub_task_type_id;
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

    public Integer getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(Integer employee_id) {
        this.employee_id = employee_id;
    }
}
