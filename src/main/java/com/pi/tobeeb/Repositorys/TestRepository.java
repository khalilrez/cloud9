package com.pi.tobeeb.Repositorys;

import com.pi.tobeeb.Entities.Test;
import com.pi.tobeeb.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TestRepository extends JpaRepository<Test,Long> {

    List<Test> findAllByFile_Appointment_Patient(User patient);
}
