package com.inft.awm.repository;


import com.inft.awm.domain.Aircraft;
import com.inft.awm.domain.Component;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ComponentRepository extends CrudRepository<Component,Integer> {

    @Query(value = "select * from component where aircraft_id = ?1",nativeQuery = true)
    Iterable<Component> findAircraftByCustomer(@Param("aircraft_id") Integer id);
}
