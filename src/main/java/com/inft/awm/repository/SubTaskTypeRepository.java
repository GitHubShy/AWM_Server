package com.inft.awm.repository;

import com.inft.awm.domain.SubTask;
import com.inft.awm.domain.SubTaskType;
import org.springframework.data.repository.CrudRepository;

public interface SubTaskTypeRepository extends CrudRepository<SubTaskType,Integer> {

//    @Query(value = "select * from job where employee_id = ?1",nativeQuery = true)
//    Iterable<Job> findJobsByEmployee(@Param("employee_id") Integer id);
//
//    @Query(value = "select * from job where id = ?1",nativeQuery = true)
//    Iterable<Job> findJob(@Param("id") Integer id);

}
