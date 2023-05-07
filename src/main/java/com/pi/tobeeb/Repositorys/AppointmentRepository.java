package com.pi.tobeeb.Repositorys;

import com.pi.tobeeb.Entities.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findAllByPatientIdUser(Long idPatient);
    List<Appointment> findAllByDoctorIdUser(Long idDoctor);

    List<Appointment> findAllByDateStart(LocalDate localDate);



    List<Appointment> findByDateStartLessThanEqual(LocalDateTime localDateTime);


}
