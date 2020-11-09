package com.inft.awm.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.inft.awm.domain.Customer;
import com.inft.awm.domain.Employee;

import java.util.Date;

/**
 * Utils for generate AWT TOKEN
 *
 * @author Yao Shi
 * @version 1.0
 * @date 30/10/2020 11:47 pm
 */
public class TokenUtils {

    //Create Customer JWT token
    public static String createCustomerToken(Customer customer) {
        String token = "";
        token = JWT.create()
                .withIssuer("customer")
                .withSubject("customer")
                .withExpiresAt(new Date(new Date().getTime() + (24 * 60 * 60 * 1000)))
                .withAudience(customer.getId().toString())
                .sign(Algorithm.HMAC256(customer.getPassword()));
        return token;
    }

    //Create Employee JWT Token
    public static String createEmployeeToken(Employee employee) {
        String token = "";
        token = JWT.create()
                .withIssuer("employee")
                .withSubject("Employee")
                .withExpiresAt(new Date(new Date().getTime() + (24 * 60 * 60 * 1000)))
                .withAudience(employee.getId().toString())
                .sign(Algorithm.HMAC256(employee.getPassword()));
        return token;
    }
}
