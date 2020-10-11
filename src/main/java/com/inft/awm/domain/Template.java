package com.inft.awm.domain;

import javax.persistence.*;

@Entity
@Table
public class Template {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

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
}

