package com.inft.awm.domain;

import com.inft.awm.utils.TimeUtils;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "The account name cannot be empty")
    @Length(min = 2, max = 50, message = "The length must be between 2 and 50")
    private String account_name;

    @NotBlank(message = "The password cannot be empty")
    private String password;

    @Email(message = "The email address format is incorrect")
    private String email;

    @NotNull(message = "The payment rate cannot be empty")
    private Float payment_rate;

    @NotBlank(message = "The first name cannot be empty")
    private String first_name;

    @NotBlank(message = "The second name cannot be empty")
    private String surname;

    private String tax_file_number;
    
    private String phone;

    private Integer gender;

    private String birth_year;

    private Integer title;

    private String portrait_url;

    public String getPortrait_url() {
        return portrait_url;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setPortrait_url(String portrait_url) {
        this.portrait_url = portrait_url;
    }

    public Integer getTitle() {
        return title;
    }

    public void setTitle(Integer title) {
        this.title = title;
    }

    public Float getPayment_rate() {
        return payment_rate;
    }

    public void setPayment_rate(Float payment_rate) {
        this.payment_rate = payment_rate;
    }

    public Integer getId() {
        return id;
    }

    public void setCustomer_id(Integer customer_id) {
        this.id = customer_id;
    }

    public String getAccount_name() {
        return account_name;
    }

    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBirth_year() {
        return birth_year;
    }

    public void setBirth_year(String birth_year) {
        this.birth_year = TimeUtils.getCurrentDate(birth_year);
    }

    public String getTax_file_number() {
        return tax_file_number;
    }

    public void setTax_file_number(String tax_file_number) {
        this.tax_file_number = tax_file_number;
    }
}
