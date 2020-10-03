package com.inft.awm.controller;

import com.inft.awm.domain.Customer;
import com.inft.awm.domain.Employee;
import com.inft.awm.mail.MailServiceImp;
import com.inft.awm.repository.CustomerRepository;
import com.inft.awm.repository.EmployeeRepository;
import com.inft.awm.response.SimpleResult;
import com.inft.awm.service.CustomerService;
import com.inft.awm.utils.StringUtils;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.Charset;
import java.util.Random;

@RestController
@RequestMapping("/awm_server/notification")
@Api(tags = "Interfaces For Customer")
@CrossOrigin
public class NotificationController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    private MailServiceImp mailServiceImp;


    @PostMapping(value = "/resetPassword")
    public SimpleResult getAllCustomer(String account_name,Integer type) {
        String email;
        String subject = "Reset Password";
        if(type == 0) {
            final Employee employee = employeeRepository.findByEmployeeName(account_name);
            if (employee == null) {
                throw new RuntimeException("This employee does not exist");
            } else {
                email = employee.getEmail();
            }
        } else {
            final Customer customer = customerRepository.findByUserName(account_name);
            if (customer == null) {
                throw new RuntimeException("This account does not exist");
            } else {
                email = customer.getEmail();
            }
        }
        String content = "Your new password is  "+StringUtils.randomStrings(8) + "  Please log in by this new password and go to profile to set a new password";
        mailServiceImp.sendSimpleMail(email,subject, content);
        return new SimpleResult("A email has been sent to your register email,please follow the instruction to reset your password");
    }
}
