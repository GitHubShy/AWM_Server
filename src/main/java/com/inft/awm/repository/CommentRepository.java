package com.inft.awm.repository;

import com.inft.awm.domain.Comment;
import com.inft.awm.domain.Job;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
/**
 * Comment CRUD
 *
 * @author Yao Shi
 * @version 1.0
 * @date 06/10/2020 09:47 pm
 */
public interface CommentRepository extends CrudRepository<Comment,Integer> {

    @Query(value = "select * from comment where job_id = ?1 ORDER BY content_time DESC",nativeQuery = true)
    Iterable<Comment> findCommentsByJob(@Param("jon_id") Integer id);

}
