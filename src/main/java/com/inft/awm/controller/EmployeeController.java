package com.inft.awm.controller;

import com.inft.awm.custom.NeedToken;
import com.inft.awm.domain.Attendance;
import com.inft.awm.domain.Employee;
import com.inft.awm.response.ResponseLogin;
import com.inft.awm.response.SimpleResult;
import com.inft.awm.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/awm_server/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @PostMapping(value = "/getAllEmployee")
    @NeedToken
    public Iterable<Employee> getAllCustomer() {
        return employeeService.getAllEmployees();
    }

    @PostMapping(value = "/register")
    public SimpleResult register(Employee employee) {
        employeeService.register(employee);
        return new SimpleResult("Success");
    }

    @PostMapping(value = "/login")
    public ResponseLogin login(Employee employee) {
        return employeeService.login(employee);
    }

    @PostMapping(value = "/clock")
    @NeedToken
    public SimpleResult clock(HttpServletRequest httpServletRequest) {
        String id = (String) httpServletRequest.getAttribute("id");
        employeeService.clock(id);
        return new SimpleResult("Success");
    }

    @PostMapping(value = "/getAttendance")
    @NeedToken
    public List<Attendance> getAttendance(HttpServletRequest httpServletRequest, String date) {
        String id = (String) httpServletRequest.getAttribute("id");
        return employeeService.findAttendance(id,date);
    }
}
