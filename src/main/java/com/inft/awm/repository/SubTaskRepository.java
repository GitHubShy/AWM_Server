package com.inft.awm.repository;

import com.inft.awm.domain.Job;
import com.inft.awm.domain.SubTask;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface SubTaskRepository extends CrudRepository<SubTask,Integer> {

//    @Query(value = "select * from job where employee_id = ?1",nativeQuery = true)
//    Iterable<Job> findJobsByEmployee(@Param("employee_id") Integer id);
//
//    @Query(value = "select * from job where id = ?1",nativeQuery = true)
//    Iterable<Job> findJob(@Param("id") Integer id);

}
