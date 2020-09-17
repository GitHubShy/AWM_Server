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

    public void register(Employee employee) {
        Employee existCustomer = employeeRepository.findByEmployeeName(employee.getAccount_name());

        if (existCustomer != null) {
            throw new RuntimeException("The employee name has already been exist");
        }

        employeeRepository.save(employee);
    }

    public ResponseLogin login(String account_name, String password) {

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
}
