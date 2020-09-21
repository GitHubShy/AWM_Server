package com.inft.awm.controller;

import com.inft.awm.custom.NeedToken;
import com.inft.awm.domain.Customer;
import com.inft.awm.response.ResponseLogin;
import com.inft.awm.response.SimpleResult;
import com.inft.awm.service.CustomerService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/awm_server/customer")
@Api(tags = "Interfaces For Customer")
@CrossOrigin
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @GetMapping(value = "/getAllCustomers")
    public Iterable<Customer> getAllCustomer() {
        return customerService.getAllCustomers();
    }

    @PostMapping(value = "/register")
    @ApiOperation(value = "Register a customer", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="body",name="user_name",required=true),
            @ApiImplicitParam(paramType="body",name="password",required=true),
            @ApiImplicitParam(paramType="body",name="email",required=true),
            @ApiImplicitParam(paramType="body",name="first_name"),
            @ApiImplicitParam(paramType="body",name="surname"),
            @ApiImplicitParam(paramType="body",name="gender"),
            @ApiImplicitParam(paramType="body",name="phone"),
            @ApiImplicitParam(paramType="body",name="birth_year"),
            @ApiImplicitParam(paramType="body",name="id",value = "Do not pass this paratmeter,because this value is used pk in database"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "success"),
            @ApiResponse(code = 1, message = "failed reason is shown in message",response = SimpleResult.class),
    })
    public SimpleResult register(Customer customer) {
        customerService.register(customer);
        return new SimpleResult("Success");
    }

    @PostMapping(value = "/login")
    @ApiOperation(value = "Customer login", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="body",name="account_name", dataTypeClass = String.class,required=true),
            @ApiImplicitParam(paramType="body",name="password",dataTypeClass = String.class,required=true),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "success",response = ResponseLogin.class),
            @ApiResponse(code = 1, message = "failed reason is shown in message",response = SimpleResult.class),
    })
    public ResponseLogin login(String account_name,String password) {
        return customerService.login(account_name,password);
    }
}
