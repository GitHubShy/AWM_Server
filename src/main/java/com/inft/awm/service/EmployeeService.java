package com.inft.awm.service;


import com.inft.awm.domain.Employee;
import com.inft.awm.repository.EmployeeRepository;
import com.inft.awm.response.ResponseLogin;
import com.inft.awm.token.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    public void register(Employee employee) {
        Employee existCustomer = employeeRepository.findByEmployeeName(employee.getUserName());

        if (existCustomer != null) {
            throw new RuntimeException("The employee name has already been exist");
        }

        employeeRepository.save(employee);
    }

    public ResponseLogin login(Employee loginEmployee) {

        Employee existCustomer = employeeRepository.findByEmployeeName(loginEmployee.getUserName());

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
}
