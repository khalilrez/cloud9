package com.pi.tobeeb.Services;

import com.pi.tobeeb.Entities.Appointment;
import com.pi.tobeeb.Entities.User;
import com.pi.tobeeb.Interfaces.AppointmentInterface;
import com.pi.tobeeb.Repositorys.AppointmentRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j

public class AppointmentService implements AppointmentInterface {
    @Autowired
    private JavaMailSender javaMailSender;
    AppointmentRepo appointmentRepo;

    @Override
    public List<Appointment> retrieveAllAppointment() {
        return appointmentRepo.findAll();
    }

    @Override
    public Appointment addAppointment(Appointment appointment) {
        LocalDateTime dateStart = appointment.getDateStart();
        List<Appointment> appointmentsOnDate = appointmentRepo.findBydateStart(dateStart);

        if (appointmentsOnDate.isEmpty()) {
            // si aucune réservation n'existe pour cette date, enregistrez simplement la nouvelle réservation
            return appointmentRepo.save(appointment);
        } else {
            // sinon, la date est déjà prise, renvoyer une erreur ou une exception
            throw new IllegalArgumentException("La date de rendez-vous est déjà prise");
        }
    }
    
    public List<Appointment> getAllAppointments(Long id) {
        return appointmentRepo.findAllByDoctorIdUser(id);
    }

    @Override
    public Appointment updateAppointment(Appointment appointment) {
        LocalDateTime currentDate = LocalDateTime.now();
        LocalDateTime reservationDate = appointment.getDateStart();

        if (reservationDate.isBefore(currentDate.plusDays(1))) {
            throw new IllegalArgumentException("Reservations can only be updated one day before the current date");
        }

        int appointmentId = appointment.getIdAppointment();
        Optional<Appointment> existingAppointment = appointmentRepo.findById(appointmentId);

        if (existingAppointment.isPresent()) {
            Appointment updatedAppointment = appointmentRepo.save(appointment);
            return updatedAppointment;
        } else {
            throw new IllegalArgumentException("Appointment with ID " + appointmentId + " not found");
        }
    }

    @Override
    public void removeAppointment(Appointment abonnement) {
        appointmentRepo.delete(abonnement);
    }
    @Override
    public void removeAppointmentById(int id) {
        appointmentRepo.deleteById(id);
    }

    @Override
    public Appointment retrieveAppointment(int idAppointment) {
        return appointmentRepo.findById(idAppointment).get();
    }



    public List<Appointment> findAppointmentsByUser(User user) {
        return appointmentRepo.findByPatient(user);
    }

    public List<Appointment> findAppointmentsByDoctor(User user) {
        return appointmentRepo.findByDoctor(user);
    }
@Override
   // @Scheduled(cron = "*/30 * * * * *") // execute every 30 seconds
    public String sendEmail() {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo("rafed.benjeddou@esprit.tn");
            message.setSubject("Test e-mail");
            message.setText("This is a test e-mail from my Spring Boot application.");
            javaMailSender.send(message);
            System.out.println("E-mail sent successfully.");
            //log.info("mail");
            return "E-mail sent successfully.";

    }
}
