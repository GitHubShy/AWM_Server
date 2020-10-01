package com.inft.awm.repository;

import com.inft.awm.domain.Aircraft;
import com.inft.awm.domain.Attendance;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface AircraftRepository extends CrudRepository<Aircraft,Integer> {

    @Query(value = "select * from aircraft where customer_id = ?1",nativeQuery = true)
    Iterable<Aircraft> findAircraftByCustomer(@Param("customer_id") Integer id);

    @Query(value = "select * from aircraft where id = ?1",nativeQuery = true)
    Iterable<Aircraft> findAircraft(@Param("id") Integer id);

}
