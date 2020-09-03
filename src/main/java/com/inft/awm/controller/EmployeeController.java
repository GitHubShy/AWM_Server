package com.inft.awm.controller;

import com.inft.awm.custom.UserLoginToken;
import com.inft.awm.domain.Customer;
import com.inft.awm.domain.Employee;
import com.inft.awm.response.ResponseLogin;
import com.inft.awm.response.SimpleResult;
import com.inft.awm.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @PostMapping(value = "/getAllEmployee")
    @UserLoginToken
    public Iterable<Employee> getAllCustomer() {
        return employeeService.getAllEmployees();
    }

    @PostMapping(value = "/register")
    public SimpleResult register(Employee employee) {
        employeeService.register(employee);
        return new SimpleResult("Success");
    }

    @PostMapping(value = "/login")
    public ResponseLogin login(Employee employee) {
        return employeeService.login(employee);
    }
}
