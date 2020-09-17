package com.inft.awm.controller;

import com.inft.awm.custom.NeedToken;
import com.inft.awm.domain.Attendance;
import com.inft.awm.domain.Employee;
import com.inft.awm.response.ResponseLogin;
import com.inft.awm.response.SimpleResult;
import com.inft.awm.service.EmployeeService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/awm_server/employee")
@Api(tags = "Interfaces For Employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @PostMapping(value = "/getAllEmployee")
    @NeedToken
    public Iterable<Employee> getAllCustomer() {
        return employeeService.getAllEmployees();
    }

    @PostMapping(value = "/register")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="body",name="account_name",required=true),
            @ApiImplicitParam(paramType="body",name="password",required=true),
            @ApiImplicitParam(paramType="body",name="email",required=true),
            @ApiImplicitParam(paramType="body",name="payment_rate",required=true),
            @ApiImplicitParam(paramType="body",name="tax_file_number",required=true),
            @ApiImplicitParam(paramType="body",name="phone",required=true),
            @ApiImplicitParam(paramType="body",name="first_name"),
            @ApiImplicitParam(paramType="body",name="surname"),
            @ApiImplicitParam(paramType="body",name="gender"),
            @ApiImplicitParam(paramType="body",name="birth_year"),
            @ApiImplicitParam(paramType="body",name="title"),
            @ApiImplicitParam(paramType="body",name="id",value = "Do not pass this paratmeter,because this value is used pk in database"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "success"),
            @ApiResponse(code = 1, message = "failed reason is shown in message",response = SimpleResult.class),
    })
    public SimpleResult register(Employee employee) {
        employeeService.register(employee);
        return new SimpleResult("Success");
    }

    @PostMapping(value = "/login")
    @ApiOperation(value = "Employee login", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="body",name="account_name", dataTypeClass = String.class,required=true),
            @ApiImplicitParam(paramType="body",name="password",dataTypeClass = String.class,required=true),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "success",response = ResponseLogin.class),
            @ApiResponse(code = 1, message = "failed reason is shown in message",response = SimpleResult.class),
    })
    public ResponseLogin login(String account_name, String password) {
        return employeeService.login(account_name,password);
    }

    @PostMapping(value = "/clock")
    @NeedToken
    @ApiOperation(value = "Employee clock on/off", notes = "Employee could clock many times within one day, but only record the earliest and latest click within one day")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="header",name="token", dataTypeClass = String.class,required=true),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "success",response = SimpleResult.class),
            @ApiResponse(code = 1, message = "failed reason is shown in message"),
    })
    public SimpleResult clock(HttpServletRequest httpServletRequest) {
        String id = (String) httpServletRequest.getAttribute("id");
        employeeService.clock(id);
        return new SimpleResult("Success");
    }

    @PostMapping(value = "/getAttendance")
    @NeedToken
    @ApiOperation(value = "Employee clock on/off", notes = "Get all attendance for a user")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="header",name="token", dataTypeClass = String.class,required=true),
            @ApiImplicitParam(paramType="body",name="date", dataTypeClass = String.class,required=true,value = "The date(2020-09-12) you want to get attendance,if null is passed, return all date attendance"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "success",response = SimpleResult.class),
            @ApiResponse(code = 1, message = "failed reason is shown in message"),
    })
    public List<Attendance> getAttendance(HttpServletRequest httpServletRequest, String date) {
        String id = (String) httpServletRequest.getAttribute("id");
        return employeeService.findAttendance(id,date);
    }
}
