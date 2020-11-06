package com.inft.awm.repository;


import com.inft.awm.domain.Customer;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * Customer CRUD
 *
 * @author Yao Shi
 * @version 1.0
 * @date 30/10/2020 11:47 pm
 */
public interface CustomerRepository extends CrudRepository<Customer,Integer> {

    @Query(value = "select * from customer where account_name = ?1",nativeQuery = true)
    Customer findByUserName(@Param("name") String userName);

    @Query(value = "select * from customer where id = ?1",nativeQuery = true)
    Customer findByCustomerId(@Param("id") Integer id);

}
