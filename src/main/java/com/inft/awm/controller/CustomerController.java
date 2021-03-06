package com.inft.awm.controller;

import com.inft.awm.custom.NeedToken;
import com.inft.awm.domain.Customer;
import com.inft.awm.domain.Receipt;
import com.inft.awm.response.ResponseCustomerLogin;
import com.inft.awm.response.ResponseLogin;
import com.inft.awm.response.SimpleResult;
import com.inft.awm.service.CustomerService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Receive request about customer
 *
 * @author Yao Shi
 * @version 1.0
 * @date 30/10/2020 16:41 pm
 */
@RestController
@RequestMapping("/awm_server/customer")
@Api(tags = "Interfaces For Customer")
@CrossOrigin
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    /**Get all customer
     * @return
     */
    @PostMapping(value = "/getAllCustomers")
    public Iterable<Customer> getAllCustomer() {
        return customerService.getAllCustomers();
    }

    /**Register a new customer
     * @param customer
     * @return
     */
    @PostMapping(value = "/register")
    @ApiOperation(value = "Register a customer", notes = "")
    @ApiResponses({
            @ApiResponse(code = 200, message = "success"),
            @ApiResponse(code = 1, message = "failed reason is shown in message",response = SimpleResult.class),
    })
    public SimpleResult register(@RequestBody Customer customer) {
        customerService.register(customer);
        return new SimpleResult("Success");
    }

    /**Customer login
     * @param account_name
     * @param password
     * @return
     */
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
    public ResponseCustomerLogin login(String account_name, String password) {
        return customerService.login(account_name,password);
    }

    /**Get a customer by id
     * @param id
     * @return
     */
    @PostMapping(value = "/getCustomer")
    @NeedToken
    public Customer getCustomer(Integer id) {
        Customer customer = customerService.findCustomerById(id);
        return customer;
    }
    @PostMapping(value = "/getReceipts")
    @NeedToken
    public List<Receipt> getReceipts(HttpServletRequest httpServletRequest) {
        String id = (String) httpServletRequest.getAttribute("id");
        return  customerService.getReceipts(Integer.valueOf(id));
    }

}
