package com.inft.awm.service;


import com.inft.awm.domain.Attendance;
import com.inft.awm.domain.Employee;
import com.inft.awm.repository.AttendanceRepository;
import com.inft.awm.repository.EmployeeRepository;
import com.inft.awm.response.ResponseLogin;
import com.inft.awm.token.TokenUtils;
import com.inft.awm.utils.StringUtils;
import com.inft.awm.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    AttendanceRepository attendanceRepository;

    public Employee register(Employee employee) {
        Employee existCustomer = employeeRepository.findByEmployeeName(employee.getAccount_name());

        if (existCustomer != null) {
            throw new RuntimeException("The employee name has already been exist");
        }

        Employee save = employeeRepository.save(employee);
        return save;
    }

    public ResponseLogin login(String account_name, String password) {

        if (StringUtils.isEmpty(account_name)) {
            throw new RuntimeException("The account name can not be empty");
        }

        Employee existCustomer = employeeRepository.findByEmployeeName(account_name);

        if (existCustomer == null) {
            throw new RuntimeException("The employee dos not exist");
        } else {
            if (!password.equals(existCustomer.getPassword())) {
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

    /**
     * @param employee_id
     * @param date if date == null, return all the user's attendances."2020-09-12"
     * @return
     */
    public List<Attendance> findAttendance(String employee_id, String date) {

        ArrayList<Attendance> attendances = new ArrayList<Attendance>();
        if (StringUtils.isEmpty(date)) {
            Iterable<Attendance> attendanceById = attendanceRepository.findAttendanceById(Integer.valueOf(employee_id));
            if (attendanceById != null) {
                Iterator<Attendance> iterator = attendanceById.iterator();
                while (iterator.hasNext()) {
                    attendances.add(iterator.next());
                }
            }

        } else {
            Attendance attendanceById = attendanceRepository.findAttendanceById(Integer.valueOf(employee_id), date);
            attendances.add(attendanceById);
        }
        return attendances;
    }

    public void updateEmployee(Employee employee){
        if (employee == null) {
            throw new RuntimeException("employee can not be null");
        }
        Employee originalEmployee = employeeRepository.findByEmployeeId(employee.getId());

        if (employeeRepository.findByEmployeeName(employee.getAccount_name()) != null && employeeRepository.findByEmployeeName(employee.getAccount_name()).getId() != employee.getId()) {
            throw new RuntimeException("This account name already exists");
        }
        if (!StringUtils.isEmpty(employee.getAccount_name())) {
            originalEmployee.setAccount_name(employee.getAccount_name());
        }
        if (!StringUtils.isEmpty(employee.getFirst_name())) {
            originalEmployee.setFirst_name(employee.getFirst_name());
        }
        if (!StringUtils.isEmpty(employee.getSurname())) {
            originalEmployee.setSurname(employee.getSurname());
        }
        if (!StringUtils.isEmpty(employee.getPassword())) {
            originalEmployee.setPassword(employee.getPassword());
        }
        if (!StringUtils.isEmpty(employee.getTax_file_number())) {
            originalEmployee.setTax_file_number(employee.getTax_file_number());
        }
        if (!StringUtils.isEmpty(employee.getEmail())) {
            originalEmployee.setEmail(employee.getEmail());
        }
        if (!StringUtils.isEmpty(employee.getPhone())) {
            originalEmployee.setPhone(employee.getPhone());
        }
        if (employee.getTitle() != null) {
            originalEmployee.setTitle(employee.getTitle());
        }
        if (employee.getPayment_rate() != null) {
            originalEmployee.setPayment_rate(employee.getPayment_rate() );
        }
        if (employee.getBirth_year() != null) {
            originalEmployee.setBirth_year(employee.getBirth_year());
        }
        if (employee.getGender() != null) {
            originalEmployee.setGender(employee.getGender());
        }
        employeeRepository.save(originalEmployee);
    }
}
