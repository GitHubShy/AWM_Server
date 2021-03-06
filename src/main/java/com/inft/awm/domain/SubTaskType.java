package com.inft.awm.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
/**
 * mapping of SubTaskType table
 *
 * @author Yao Shi
 * @version 1.0
 * @date 30/10/2020 11:56 pm
 */
@Entity
@Table(name = "sub_task_type")
public class SubTaskType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * sub task title
     * such as Inspect_Engine,Inspect_Spark_Plugs
     */
    private String title;

    /**
     * sub task description
     */
    private String description;

    private String materials;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getMaterials() {
        return materials;
    }

    public void setMaterials(String materials) {
        this.materials = materials;
    }
}
