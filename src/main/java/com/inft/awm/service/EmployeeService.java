package com.inft.awm.service;


import com.inft.awm.domain.Attendance;
import com.inft.awm.domain.Employee;
import com.inft.awm.repository.AttendanceRepository;
import com.inft.awm.repository.EmployeeRepository;
import com.inft.awm.response.*;
import com.inft.awm.token.TokenUtils;
import com.inft.awm.utils.EmployeeUtils;
import com.inft.awm.utils.StringUtils;
import com.inft.awm.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
/**
 * Handle employee logic
 *
 * @author Yao Shi
 * @version 1.0
 * @date 30/10/2020 11:47 pm
 */
@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    AttendanceRepository attendanceRepository;

    /**Register a employee
     * @param employee
     * @return
     */
    public Employee register(Employee employee) {
        Employee existCustomer = employeeRepository.findByEmployeeName(employee.getAccount_name());

        if (existCustomer != null) {
            throw new RuntimeException("The employee name has already been exist");
        }

        Employee save = employeeRepository.save(employee);
        return save;
    }

    /** Employee Login
     * @param account_name
     * @param password
     * @return
     */
    public ResponseEmployeeLogin login(String account_name, String password) {

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
                return new ResponseEmployeeLogin(token,existCustomer);
            }
        }
    }

    /**Get all employee
     * @return
     */
    public Iterable<Employee> getAllEmployees() {
        Iterable<Employee> allCustomers = employeeRepository.findAll();
        return allCustomers;
    }

    /** Find a employee by id
     * @param customerId
     * @return
     */
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
            attendanceById.setWork_hours(TimeUtils.getDateDiffHours(attendanceById.getOn_time(),attendanceById.getOff_time(),"HH:mm:ss"));
            double workingHours = TimeUtils.getDateDiffHours(attendanceById.getOn_time(),attendanceById.getOff_time(),"HH:mm:ss");
        }
        attendanceRepository.save(attendanceById);
    }

    /** Find attendace for a employee
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

    /**Update a emloyee
     * @param employee
     */
    public void updateEmployee(Employee employee){
        if (employee == null) {
            throw new RuntimeException("employee can not be null");
        }
        Employee originalEmployee = employeeRepository.findByEmployeeId(employee.getId());

        if (employeeRepository.findByEmployeeName(employee.getAccount_name()) != null && employeeRepository.findByEmployeeName(employee.getAccount_name()).getId() != employee.getId()) {
            throw new RuntimeException("This account name already exists");
        }
        //Update account name
        if (!StringUtils.isEmpty(employee.getAccount_name())) {
            originalEmployee.setAccount_name(employee.getAccount_name());
        }
        //update firstname
        if (!StringUtils.isEmpty(employee.getFirst_name())) {
            originalEmployee.setFirst_name(employee.getFirst_name());
        }
        //update surname
        if (!StringUtils.isEmpty(employee.getSurname())) {
            originalEmployee.setSurname(employee.getSurname());
        }
        //update password
        if (!StringUtils.isEmpty(employee.getPassword())) {
            originalEmployee.setPassword(employee.getPassword());
        }
        //update TFN
        if (!StringUtils.isEmpty(employee.getTax_file_number())) {
            originalEmployee.setTax_file_number(employee.getTax_file_number());
        }
        //update employee
        if (!StringUtils.isEmpty(employee.getEmail())) {
            originalEmployee.setEmail(employee.getEmail());
        }
        //update phone number
        if (!StringUtils.isEmpty(employee.getPhone())) {
            originalEmployee.setPhone(employee.getPhone());
        }
        //update title
        if (employee.getTitle() != null) {
            originalEmployee.setTitle(employee.getTitle());
        }
        //update payment rate
        if (employee.getPayment_rate() != null) {
            originalEmployee.setPayment_rate(employee.getPayment_rate() );
        }
        //update birth
        if (employee.getBirth_year() != null) {
            originalEmployee.setBirth_year(employee.getBirth_year());
        }
        //update gender
        if (employee.getGender() != null) {
            originalEmployee.setGender(employee.getGender());
        }
        employeeRepository.save(originalEmployee);
    }

    /** Get employee by different type
     * @param type -1:all 0: Engineer   1: Manager  99:Super Administrator
     */
    public List<ResponseEmployeeType> getEmployeeByType(int type) {
        Iterable<Employee> employees;
        if (type == -1) {
            employees = employeeRepository.findAll();
        } else{
            employees = employeeRepository.findByType(type);
        }
        Iterator<Employee> iterator = employees.iterator();
        List<ResponseEmployeeType> result = new ArrayList<>();
        while (iterator.hasNext()) {
            Employee employee = iterator.next();
            String name = employee.getFirst_name() + " " + employee.getSurname();
            ResponseEmployeeType data = new ResponseEmployeeType(employee.getId(),name, EmployeeUtils.getEmployeeType(employee.getTitle()));
            result.add(data);
        }
        return result;
    }

    /**Update employee's portrait
     * @param httpServletRequest
     * @param portraitUrl
     */
    public void updatePortrait (HttpServletRequest httpServletRequest, String portraitUrl) {
        if (StringUtils.isEmpty(portraitUrl)) {
            throw new RuntimeException("The url is null");
        }
        String id = (String) httpServletRequest.getAttribute("id");
        Integer employeeId = Integer.valueOf(id);
        Employee employee = employeeRepository.findById(employeeId).get();
        employee.setPortrait_url(portraitUrl);
        employeeRepository.save(employee);

    }

    /** Get salary record for login employee
     * @param httpServletRequest
     * @return
     */
    public List<ResponseMonthlySalary> getMonthlySalary(HttpServletRequest httpServletRequest) {
        String id = (String) httpServletRequest.getAttribute("id");
        Integer employeeId = Integer.valueOf(id);
        //Get employee
        Employee employee = employeeRepository.findById(employeeId).get();
        //Get salary
        Float payment_rate = employee.getPayment_rate();
        //Get attendance
        Iterable<Attendance> attendances = attendanceRepository.findAttendanceById(employeeId);
        Iterator<Attendance> iterator = attendances.iterator();
        //Create result
        ArrayList<ResponseMonthlySalary> result = new ArrayList<>();
        while (iterator.hasNext()) {
            Attendance attendance = iterator.next();
            String date = attendance.getDate();
            SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat sd1 = new SimpleDateFormat("yyyy-MM");
            SimpleDateFormat sd2 = new SimpleDateFormat("MM-dd");
            try {
                Date newDate = sd.parse(date);
                String yyyyMM = sd1.format(newDate);
                System.out.println(yyyyMM);
                //Create a new monthly salary object
                ResponseMonthlySalary parent = new ResponseMonthlySalary(yyyyMM,0,0,employee.getPayment_rate(),new ArrayList<ResponseDailySalary>());;
                boolean isExist = false;
                for (ResponseMonthlySalary monthlySalary : result) {
                    if (yyyyMM.equals(monthlySalary.getDate())) {//The monthly salary object has already exist.
                        parent = monthlySalary;
                        isExist = true;
                        break;
                    }
                }
                String MMdd = sd2.format(newDate);
                ResponseDailySalary dailySalary = new ResponseDailySalary(MMdd,attendance.getWork_hours(),payment_rate * attendance.getWork_hours(),payment_rate,attendance.getOn_time(),attendance.getOff_time());
                parent.getDaily_salary().add(dailySalary);
                parent.setSalary(parent.getSalary()+dailySalary.getSalary());
                parent.setWork_hours(parent.getWork_hours()+dailySalary.getWork_hours());
                if (!isExist) {
                    result.add(parent);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

}
