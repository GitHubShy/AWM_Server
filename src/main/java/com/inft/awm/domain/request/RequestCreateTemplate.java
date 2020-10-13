package com.inft.awm.domain.request;

import java.util.ArrayList;

public class RequestCreateTemplate {

    /**
     * IF employee_id = 0, this means this template is a system default template
     */
    private Integer employee_id;

    /**
     * template title
     * such as Engine_Overhaul,Electronic Maintenance
     */
    private String title;

    /**
     * template description
     */
    private String description;

    private ArrayList<Integer> subTaskTypeIds;

    public RequestCreateTemplate() {
    }

    public RequestCreateTemplate(Integer employee_id, String title, String description, ArrayList<Integer> subTaskTypeIds) {
        this.employee_id = employee_id;
        this.title = title;
        this.description = description;
        this.subTaskTypeIds = subTaskTypeIds;
    }

    public Integer getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(Integer employee_id) {
        this.employee_id = employee_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<Integer> getSubTaskTypeIds() {
        return subTaskTypeIds;
    }

    public void setSubTaskTypeIds(ArrayList<Integer> subTaskTypeIds) {
        this.subTaskTypeIds = subTaskTypeIds;
    }
}
