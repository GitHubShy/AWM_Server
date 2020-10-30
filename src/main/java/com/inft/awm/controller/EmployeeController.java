package com.inft.awm.controller;

import com.inft.awm.custom.NeedToken;
import com.inft.awm.domain.Attendance;
import com.inft.awm.domain.Employee;
import com.inft.awm.domain.request.RequestDeleteEmployee;
import com.inft.awm.domain.request.RequestGetEmployee;
import com.inft.awm.domain.request.RequestLogin;
import com.inft.awm.repository.EmployeeRepository;
import com.inft.awm.response.ResponseEmployeeLogin;
import com.inft.awm.response.ResponseEmployeeType;
import com.inft.awm.response.ResponseLogin;
import com.inft.awm.response.SimpleResult;
import com.inft.awm.service.EmployeeService;
import com.inft.awm.utils.StringUtils;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/awm_server/employee")
@Api(tags = "Interfaces For Employee")
@CrossOrigin
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    EmployeeRepository employeeRepository;

    @PostMapping(value = "/getAllEmployee")
    @NeedToken
    public Iterable<Employee> getAllEmpoyees() {
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
    public Employee register(@RequestBody Employee employee) {
        Employee newEmployee = employeeService.register(employee);
        return newEmployee;
    }

    @PostMapping(value = "/login")
    @ApiOperation(value = "Employee login", notes = "")
    @ApiResponses({
            @ApiResponse(code = 200, message = "success",response = ResponseLogin.class),
            @ApiResponse(code = 1, message = "failed reason is shown in message",response = SimpleResult.class),
    })
    public ResponseEmployeeLogin login(@RequestBody RequestLogin rl) {
        return employeeService.login(rl.getAccount_name(),rl.getPassword());
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

    @PostMapping(value = "/getEmployee")
    @NeedToken
    public Employee getEmployee(HttpServletRequest httpServletRequest, @RequestBody RequestGetEmployee requestEmployee) {
        String acturalId = requestEmployee.getId();
        if (StringUtils.isEmpty(requestEmployee.getId()) || "0".equals(requestEmployee.getId())) {
            acturalId = (String) httpServletRequest.getAttribute("id");
        } else {
            acturalId = requestEmployee.getId();
        }
        Employee user = employeeRepository.findByEmployeeId(Integer.valueOf(acturalId));
        return user;
    }

    @PostMapping(value = "/updateEmployee")
    @NeedToken
    public SimpleResult updateEmployee(HttpServletRequest httpServletRequest, @RequestBody Employee employee) {
        employeeService.updateEmployee(employee);
        return new SimpleResult("Success");
    }

    @PostMapping(value = "/deleteEmployee")
    @NeedToken
    public SimpleResult deleteEmployee(HttpServletRequest httpServletRequest, @RequestBody RequestDeleteEmployee employee) {
        employeeRepository.deleteById(employee.getId());
        return new SimpleResult("Success");
    }

    /**
     * @param type 0: Engineer   1: Manager  99:Super Administrator
     * @return
     */
    @PostMapping(value = "/getEmployeeByType")
    @NeedToken
    public List<ResponseEmployeeType> getEmployeeByType(Integer type) {
        return employeeService.getEmployeeByType(type);
    }

    @PostMapping(value = "/updatePortrait")
    @NeedToken
    public SimpleResult updatePortrait(HttpServletRequest httpServletRequest, String url) {
        employeeService.updatePortrait(httpServletRequest,url);
        return new SimpleResult("Success");
    }
}
