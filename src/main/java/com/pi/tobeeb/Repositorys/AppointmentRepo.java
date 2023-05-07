package com.pi.tobeeb.Repositorys;

import com.pi.tobeeb.Entities.Appointment;
import com.pi.tobeeb.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepo extends JpaRepository<Appointment, Integer> {
    List<Appointment> findBydateStart(LocalDateTime dateStart);
    List<Appointment> findByPatient(User user);
    List<Appointment> findByDoctor(User user);




    List<Appointment> findAllByPatientIdUser(Long idPatient);
    List<Appointment> findAllByDoctorIdUser(Long idDoctor);

    List<Appointment> findAllByDateStart(LocalDate localDate);

    List<Appointment> findByDateStartLessThanEqual(LocalDate localDate);

}
