package com.inft.awm.repository;


import com.inft.awm.domain.SubTaskType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
/**
 * SubTaskType CRUD
 *
 * @author Yao Shi
 * @version 1.0
 * @date 30/10/2020 11:47 pm
 */
public interface SubTaskTypeRepository extends CrudRepository<SubTaskType,Integer> {

    @Query(value = "select * from sub_task_type where id = ?1",nativeQuery = true)
    SubTaskType findSubTaskType(@Param("id") Integer id);
//
//    @Query(value = "select * from job where id = ?1",nativeQuery = true)
//    Iterable<Job> findJob(@Param("id") Integer id);

}
