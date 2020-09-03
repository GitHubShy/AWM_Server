package com.inft.awm.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.inft.awm.domain.Customer;
import com.inft.awm.domain.Employee;


public class TokenUtils {

    public static String createCustomerToken(Customer customer) {
        String token = "";
        token = JWT.create()
                .withIssuer("customer")
                .withAudience(customer.getCustomer_id().toString())
                .sign(Algorithm.HMAC256(customer.getPassword()));
        return token;
    }

    public static String createEmployeeToken(Employee employee) {
        String token = "";
        token = JWT.create()
                .withIssuer("employee")
                .withAudience(employee.getId().toString())
                .sign(Algorithm.HMAC256(employee.getPassword()));
        return token;
    }
}
