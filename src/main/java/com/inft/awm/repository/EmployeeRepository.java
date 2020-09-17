package com.inft.awm.repository;


import com.inft.awm.domain.Customer;
import com.inft.awm.domain.Employee;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface EmployeeRepository extends CrudRepository<Employee,Integer> {

    @Query(value = "select * from employee where account_name = ?1",nativeQuery = true)
    Employee findByEmployeeName(@Param("name") String userName);

    @Query(value = "select * from employee where id = ?1",nativeQuery = true)
    Employee findByEmployeeId(@Param("id") Integer id);

}
