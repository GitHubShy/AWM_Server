package com.inft.awm.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.inft.awm.domain.Customer;


public class TokenUtils {

    public static String getToken(Customer customer) {
        String token="";
        token= JWT.create().withAudience(customer.getCustomer_id().toString())
                .sign(Algorithm.HMAC256(customer.getPassword()));
        return token;
    }
}
