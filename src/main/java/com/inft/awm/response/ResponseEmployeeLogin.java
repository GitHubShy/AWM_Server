package com.inft.awm.response;



import com.inft.awm.domain.Employee;

public class ResponseEmployeeLogin {

    private String token;

    private Employee employee;

    public ResponseEmployeeLogin(String token, Employee employee) {
        this.token = token;
        this.employee = employee;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Employee getCustomer() {
        return employee;
    }

    public void setCustomer(Employee employee) {
        this.employee = employee;
    }
}
