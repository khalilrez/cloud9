package com.pi.tobeeb.Interfaces;

import com.pi.tobeeb.Entities.Appointment;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

public interface AppointmentInterface {

    List<Appointment> retrieveAllAppointment();
    Appointment addAppointment(Appointment appointment);

    Appointment updateAppointment(Appointment appointment);
    void removeAppointment(Appointment abonnement);
     void removeAppointmentById(int id);
    Appointment retrieveAppointment (int idAppointment);



    String sendEmail();
}
