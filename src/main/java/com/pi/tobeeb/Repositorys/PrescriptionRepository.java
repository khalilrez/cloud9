package com.pi.tobeeb.Repositorys;

import com.pi.tobeeb.Entities.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrescriptionRepository extends JpaRepository<Prescription,Long> {
}
