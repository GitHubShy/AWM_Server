package com.inft.awm.repository;

import com.inft.awm.domain.Job;
import com.inft.awm.domain.SubTask;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface SubTaskRepository extends CrudRepository<SubTask,Integer> {

    @Query(value = "select * from sub_task where job_id = ?1",nativeQuery = true)
    Iterable<SubTask> findSubTasksByJob(@Param("job_id") Integer job_id);

    @Query(value = "select * from sub_task where employee_id = ?1",nativeQuery = true)
    Iterable<SubTask> findSubTaskByEmployee(@Param("employee_id") Integer id);

}
