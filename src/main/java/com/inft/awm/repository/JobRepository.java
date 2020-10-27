package com.inft.awm.repository;

import com.inft.awm.domain.Aircraft;
import com.inft.awm.domain.Job;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface JobRepository extends CrudRepository<Job,Integer> {

    @Query(value = "select * from job where employee_id = ?1",nativeQuery = true)
    Iterable<Job> findJobsByEmployee(@Param("employee_id") Integer id);

    @Query(value = "select * from job where id = ?1",nativeQuery = true)
    Iterable<Job> findJob(@Param("id") Integer id);

    @Query(value = "select * from job where  status = ifnull(?1,status) order by id DESC",nativeQuery = true)
    Iterable<Job> findAllDesc(@Param("status") Integer status);

}
