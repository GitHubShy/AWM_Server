package com.inft.awm.repository;

import com.inft.awm.domain.Receipt;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * Receipt CRUD
 *
 * @author Yao Shi
 * @version 1.0
 * @date 30/10/2020 13:55 pm
 */
public interface ReceiptRepository extends CrudRepository<Receipt,Integer> {

    @Query(value = "select * from receipt where customer_id = ?1",nativeQuery = true)
    Iterable<Receipt> findCustomerReceipts(@Param("customer_id") Integer id);


}
