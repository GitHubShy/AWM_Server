package com.inft.awm.domain;

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

    @NotNull(message = "The TFN cannot be empty")
    private Long tax_file_number;

    @NotNull(message = "The payment rate cannot be empty")
    private Float payment_rate;

    @NotNull(message = "The payment rate cannot be empty")
    private Long phone;

    private String first_name;

    private String surname;

    private Integer gender;

    private Integer birth_year;

    private Integer title;

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

    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    public Integer getBirth_year() {
        return birth_year;
    }

    public void setBirth_year(Integer birth_year) {
        this.birth_year = birth_year;
    }

    public Long getTax_file_number() {
        return tax_file_number;
    }

    public void setTax_file_number(Long tax_file_number) {
        this.tax_file_number = tax_file_number;
    }
}
