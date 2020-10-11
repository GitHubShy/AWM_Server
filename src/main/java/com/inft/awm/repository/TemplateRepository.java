package com.inft.awm.repository;

import com.inft.awm.domain.SubTaskType;
import com.inft.awm.domain.Template;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface TemplateRepository extends CrudRepository<Template,Integer> {

    @Query(value = "select * from template where employee_id = 1 or employee_id = ?1",nativeQuery = true)
    Iterable<Template> findAvailableTemplates(@Param("employee_id") Integer id);
//
//    @Query(value = "select * from job where id = ?1",nativeQuery = true)
//    Iterable<Job> findJob(@Param("id") Integer id);

}
