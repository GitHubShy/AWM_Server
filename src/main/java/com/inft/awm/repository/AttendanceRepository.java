package com.inft.awm.repository;


import com.inft.awm.domain.Attendance;
import com.inft.awm.domain.Employee;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


public interface AttendanceRepository extends CrudRepository<Attendance,Integer> {


    @Query(value = "select * from attendance where employee_id = ?1 and date = ?2",nativeQuery = true)
    Attendance findAttendanceById(@Param("employee_id") Integer id,@Param("date") String date);

    @Query(value = "select * from attendance where employee_id = ?1",nativeQuery = true)
    Iterable<Attendance> findAttendanceById(@Param("employee_id") Integer id);

}
