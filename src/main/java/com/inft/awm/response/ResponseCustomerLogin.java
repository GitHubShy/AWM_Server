package com.inft.awm.response;


import com.inft.awm.domain.Customer;
/**
 * A class used for responsing login request
 *
 * @author Yao Shi
 * @version 1.0
 * @date 2020/10/30 11:47 pm
 */
public class ResponseCustomerLogin {

    private String token;

    private Customer customer;

    public ResponseCustomerLogin(String token,Customer customer) {
        this.token = token;
        this.customer = customer;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
