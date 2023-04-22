package com.pi.tobeeb.Repositorys;

import com.pi.tobeeb.Entities.PharmacyLocation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface PharmacyLocationRepository extends CrudRepository<PharmacyLocation, Integer> {


    @Query("SELECT pl FROM PharmacyLocation pl WHERE pl.name LIKE %:searchTerm%")
    public List<PharmacyLocation> search(@Param("searchTerm") String searchTerm);
}
