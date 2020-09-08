package com.inft.awm.controller;

import com.inft.awm.custom.NeedToken;
import com.inft.awm.domain.Customer;
import com.inft.awm.response.ResponseLogin;
import com.inft.awm.response.SimpleResult;
import com.inft.awm.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @PostMapping(value = "/getAllCustomers")
    @NeedToken
    public Iterable<Customer> getAllCustomer() {
        return customerService.getAllCustomers();
    }

    @PostMapping(value = "/register")
    public SimpleResult register(Customer customer) {
        customerService.register(customer);
        return new SimpleResult("Success");
    }

    @PostMapping(value = "/login")
    public ResponseLogin login(Customer customer) {
        return customerService.login(customer);
    }
}
