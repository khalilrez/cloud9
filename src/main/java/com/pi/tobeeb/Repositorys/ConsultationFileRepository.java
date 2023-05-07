package com.pi.tobeeb.Repositorys;

import com.pi.tobeeb.Entities.ConsultationFile;
import com.pi.tobeeb.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ConsultationFileRepository extends JpaRepository<ConsultationFile,Long> {

    Optional<ConsultationFile> findByAppointment_IdAppointment(int idAppointment);

    List<ConsultationFile> findAllByAppointment_Patient(User user);
}
