package com.inft.awm.controller;

import com.inft.awm.custom.NeedToken;
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
/**
 * Receive request about notification
 *
 * @author Yao Shi
 * @version 1.0
 * @date 30/10/2020 15:44 pm
 */
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


    /**Reset pasword
     * @param account_name
     * @param type
     * @return
     */
    @PostMapping(value = "/resetPassword")
    public SimpleResult resetPassword(String account_name, Integer type) {
        String email;
        String subject = "Reset Password";
        //Generate a new password
        String newPassword = StringUtils.randomStrings(8);
        if (type == 0) {//Employee
            final Employee employee = employeeRepository.findByEmployeeName(account_name);
            if (employee == null) {
                throw new RuntimeException("This employee does not exist");
            } else {
                employee.setPassword(newPassword);
                employeeRepository.save(employee);
                email = employee.getEmail();
            }
        } else {//Customer
            final Customer customer = customerRepository.findByUserName(account_name);
            if (customer == null) {
                throw new RuntimeException("This account does not exist");
            } else {
                customer.setPassword(newPassword);
                customerRepository.save(customer);
                email = customer.getEmail();
            }
        }
        //Sending reset password to the login people
        String content = "Your new password is  " + newPassword + "  Please log in by this new password and go to profile to set a new password";
        mailServiceImp.sendSimpleMail(email, subject, content);
        return new SimpleResult("A email has been sent to your register email,please follow the instruction to reset your password");
    }

    /** Send a message to employee or customer email
     * @param receiverType 0=customer, 1=staff
     * @param receiverName
     * @param subject
     * @param content
     * @return
     */
    @PostMapping(value = "/sendMessage")
    @NeedToken
    public SimpleResult sendMessage(int receiverType, String receiverName, String subject, String content) {
        String email;
        String s = subject;
        String c = content;
        if (receiverType == 0) {
            Customer customer = customerRepository.findByUserName(receiverName);
            if (customer == null) {
                throw new RuntimeException("This account does not exist");
            } else {
                email = customer.getEmail();
            }
        } else {
            final Employee employee = employeeRepository.findByEmployeeName(receiverName);
            if (employee == null) {
                throw new RuntimeException("This employee does not exist");
            } else {
                email = employee.getEmail();
            }
        }
        mailServiceImp.sendSimpleMail(email, subject, content);
        return new SimpleResult("success");
    }
}
