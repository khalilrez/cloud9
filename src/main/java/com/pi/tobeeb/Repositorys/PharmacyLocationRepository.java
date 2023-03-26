package com.pi.tobeeb.Repositorys;

import com.pi.tobeeb.Entities.Order2;
import com.pi.tobeeb.Entities.PharmacyLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface PharmacyLocationRepository extends JpaRepository<PharmacyLocation, Integer> {
}
