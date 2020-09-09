package com.inft.awm.service;


import com.inft.awm.domain.Attendance;
import com.inft.awm.domain.Employee;
import com.inft.awm.repository.AttendanceRepository;
import com.inft.awm.repository.EmployeeRepository;
import com.inft.awm.response.ResponseLogin;
import com.inft.awm.token.TokenUtils;
import com.inft.awm.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    AttendanceRepository attendanceRepository;

    public void register(Employee employee) {
        Employee existCustomer = employeeRepository.findByEmployeeName(employee.getUser_name());

        if (existCustomer != null) {
            throw new RuntimeException("The employee name has already been exist");
        }

        employeeRepository.save(employee);
    }

    public ResponseLogin login(Employee loginEmployee) {

        Employee existCustomer = employeeRepository.findByEmployeeName(loginEmployee.getUser_name());

        if (existCustomer == null) {
            throw new RuntimeException("The employee dos not exist");
        } else {
            if (!loginEmployee.getPassword().equals(existCustomer.getPassword())) {
                throw new RuntimeException("The password is wrong");
            } else {
                String token = TokenUtils.createEmployeeToken(existCustomer);
                return new ResponseLogin(token);
            }
        }
    }

    public Iterable<Employee> getAllEmployees() {
        Iterable<Employee> allCustomers = employeeRepository.findAll();
        return allCustomers;
    }

    public Employee findEmployeeById(Integer customerId) {
        return employeeRepository.findByEmployeeId(customerId);
    }

    public void clock(String employee_id) {
        String currentDate = TimeUtils.getCurrentDate();
        Attendance attendanceById = attendanceRepository.findAttendanceById(Integer.valueOf(employee_id), currentDate);
        System.out.println(attendanceById);
        if (attendanceById ==null) {
            attendanceById = new Attendance(Integer.valueOf(employee_id),currentDate, TimeUtils.getCurrentTime());
        } else {
            attendanceById.setOff_time(TimeUtils.getCurrentTime());
        }
        attendanceRepository.save(attendanceById);
    }
}
