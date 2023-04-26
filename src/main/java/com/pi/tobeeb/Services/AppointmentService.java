package com.pi.tobeeb.Services;

import com.pi.tobeeb.Entities.Appointment;
import com.pi.tobeeb.Repositorys.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentService {
    @Autowired
    private AppointmentRepository appointmentRepository;

    public Appointment addAppointment(Appointment appointment){
        return appointmentRepository.saveAndFlush(appointment);
    }

    public List<Appointment> findAllAppointmentsByPatientId(Long patientId){
        return appointmentRepository.findAllByPatientIdUser(patientId);
    }

    public List<Appointment> findAllAppointmentsByDoctorId(Long doctorId){
        return appointmentRepository.findAllByDoctorIdUser(doctorId);
    }

    public Appointment updateAppointment(Appointment appointment){
        return appointmentRepository.saveAndFlush(appointment);
    }

}
