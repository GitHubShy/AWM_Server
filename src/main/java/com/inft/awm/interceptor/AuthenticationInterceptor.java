package com.inft.awm.interceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;

import com.inft.awm.custom.NoNeedToken;
import com.inft.awm.custom.NeedToken;
import com.inft.awm.domain.Customer;
import com.inft.awm.domain.Employee;
import com.inft.awm.service.CustomerService;
import com.inft.awm.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class AuthenticationInterceptor implements HandlerInterceptor {



    @Autowired
    CustomerService customerService;
    @Autowired
    EmployeeService employeeService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");// 从 http 请求头中取出 token

        // 如果不是映射到方法直接通过
        if(!(handler instanceof HandlerMethod)){
            return true;
        }

        if("OPTIONS".equals(request.getMethod().toUpperCase())) {
            return true;
        }

        HandlerMethod handlerMethod=(HandlerMethod)handler;
        Method method=handlerMethod.getMethod();

        //检查是否有passtoken注释，有则跳过认证
        if (method.isAnnotationPresent(NoNeedToken.class)) {
            NoNeedToken passToken = method.getAnnotation(NoNeedToken.class);
            if (passToken.required()) {
                return true;
            }
        }
        //检查有没有需要用户权限的注解
        if (method.isAnnotationPresent(NeedToken.class)) {
            NeedToken userLoginToken = method.getAnnotation(NeedToken.class);
            if (userLoginToken.required()) {
                // 执行认证
                if (token == null) {
                    throw new RuntimeException("There is no token, please login");
                }
                // 获取 token 中的 user id
                String type;
                String id;
                try {
                    type = JWT.decode(token).getIssuer();
                } catch (JWTDecodeException j) {
                    throw new RuntimeException("Can not get user type");
                }

                try {
                    id = JWT.decode(token).getAudience().get(0);
                    if ("customer".equals(type)) {
                        Customer customer = customerService.findCustomerById(Integer.valueOf(id));
                        if (customer == null) {
                            throw new RuntimeException("The user does not exist，please register");
                        }
                        // 验证 token
                        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(customer.getPassword())).build();
                        try {
                            jwtVerifier.verify(token);
                        } catch (JWTVerificationException e) {
                            throw new RuntimeException("customer verify token failed");
                        }
                    } else {
                        Employee employee = employeeService.findEmployeeById(Integer.valueOf(id));
                        if (employee == null) {
                            throw new RuntimeException("he employee does not exist，please register");
                        }
                        // 验证 token
                        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(employee.getPassword())).build();
                        try {
                            jwtVerifier.verify(token);
                        } catch (JWTVerificationException e) {
                            throw new RuntimeException("employee verify token failed");
                        }
                    }
                    request.setAttribute("id",id);
                } catch (JWTDecodeException j) {
                    throw new RuntimeException("Can not get user type");
                }


                return true;
            }
        }


        return true;
    }
}
