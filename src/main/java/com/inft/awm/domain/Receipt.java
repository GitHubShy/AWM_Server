package com.inft.awm.domain;

import javax.persistence.*;

@Table
@Entity
public class Receipt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer job_id;

    private Integer customer_id;

    private String delivery_time;

    private String create_time;

    private Double price;

    private String crn;

    public Receipt() {
    }

    public Receipt(Integer job_id, Integer customer_id, String delivery_time, String create_time, Double price, String crn) {
        this.job_id = job_id;
        this.customer_id = customer_id;
        this.delivery_time = delivery_time;
        this.create_time = create_time;
        this.price = price;
        this.crn = crn;
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

    public Integer getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(Integer customer_id) {
        this.customer_id = customer_id;
    }

    public String getDelivery_time() {
        return delivery_time;
    }

    public void setDelivery_time(String delivery_time) {
        this.delivery_time = delivery_time;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getCrn() {
        return crn;
    }

    public void setCrn(String crn) {
        this.crn = crn;
    }
}
