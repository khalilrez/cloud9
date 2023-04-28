package com.pi.tobeeb.Services;

import com.pi.tobeeb.Entities.Appointment;
import com.pi.tobeeb.Entities.User;
import com.pi.tobeeb.Exceptions.ResourceNotFoundException;
import com.pi.tobeeb.Repositorys.AppointmentRepository;
import com.pi.tobeeb.Repositorys.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AppointmentService {
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotificationService notificationService;

    @Scheduled(cron = "0 0 * * * *") // run every hour
    public void sendAppointmentNotifications() {
        LocalDate currentDate = LocalDate.now();
        List<Appointment> appointments = appointmentRepository.findAllByDateStart(currentDate);
        for (Appointment appointment : appointments) {
            notificationService.sendNotification(appointment);
        }
    }


    public Appointment createAppointment(Long patientId,Long doctorId,Appointment appointment) {
        Appointment newAppointment = new Appointment();
        User patient = userRepository.findById(patientId).orElseThrow(() -> new ResourceNotFoundException("Patient", "id", patientId));
        User doctor = userRepository.findById(patientId).orElseThrow(() -> new ResourceNotFoundException("Doctor", "id", doctorId));
        newAppointment.setType(appointment.getType());
        newAppointment.setDateStart(appointment.getDateStart());
        newAppointment.setPatient(patient);
        newAppointment.setDoctor(doctor);
        return appointmentRepository.save(newAppointment);
    }

    public Appointment getAppointment(Long id) {
        return appointmentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Appointment", "id", id));
    }

    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    public Appointment updateAppointment(Long id, Appointment AppointmentDetails) {
        Appointment Appointment = appointmentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Appointment", "id", id));
        Appointment.setType(AppointmentDetails.getType());
        Appointment.setDateStart(AppointmentDetails.getDateStart());
        Appointment.setConsultationFile(AppointmentDetails.getConsultationFile());
        return appointmentRepository.save(Appointment);
    }

    public void deleteAppointment(Long id) {
        Appointment Appointment = appointmentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Appointment", "id", id));
        appointmentRepository.delete(Appointment);
    }

}
