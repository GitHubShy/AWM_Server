package com.inft.awm.domain;

import javax.persistence.*;
/**
 * mapping of TemplateTask table
 *
 * @author Yao Shi
 * @version 1.0
 * @date 30/10/2020 11:47 pm
 */
@Entity
@Table(name = "template_task")
public class TemplateTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer template_id;

    private Integer sub_task_type_id;

    public TemplateTask() {
    }

    public TemplateTask(Integer template_id, Integer sub_task_type_id) {
        this.template_id = template_id;
        this.sub_task_type_id = sub_task_type_id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTemplate_id() {
        return template_id;
    }

    public void setTemplate_id(Integer template_id) {
        this.template_id = template_id;
    }

    public Integer getSub_task_type_id() {
        return sub_task_type_id;
    }

    public void setSub_task_type_id(Integer sub_task_type_id) {
        this.sub_task_type_id = sub_task_type_id;
    }
}
