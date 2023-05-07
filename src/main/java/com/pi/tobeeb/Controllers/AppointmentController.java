package com.pi.tobeeb.Controllers;


import com.pi.tobeeb.Dto.AppointmentDTO;
import com.pi.tobeeb.Entities.Appointment;
import com.pi.tobeeb.Payload.appointments.CreateAppointmentRequest;
import com.pi.tobeeb.Repositorys.AppointmentRepository;
import com.pi.tobeeb.Services.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import com.pi.tobeeb.Entities.Appointment;
import com.pi.tobeeb.Entities.User;
import com.pi.tobeeb.Repositorys.AppointmentRepo;
import com.pi.tobeeb.Services.AppointmentService;
import com.pi.tobeeb.Services.EmailService;
import com.pi.tobeeb.Services.PaymentService;
import com.pi.tobeeb.Services.TwilioService;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.PaymentSource;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@AllArgsConstructor

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/appointments")
public class AppointmentController {
    @Autowired
    AppointmentService appointmentService;
    @Autowired

    EmailService emailService;

    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private PaymentService paymentService;

     AppointmentRepo appointmentRepository;
    @GetMapping("/appointment")

    public List<Appointment> getCours()
    {
        return appointmentService.retrieveAllAppointment();

    }
    @GetMapping("/send-email")
    @Scheduled(cron = "0 0 8 * * ?") // execute every day at 8 AM
    public String sendEmail() {
        try {
            // Get appointments from the database
            List<Appointment> appointments = appointmentRepository.findAll();

            // Get current date
            LocalDate currentDate = LocalDate.now();

            // Check each appointment for a dateStart one day before current date
            for (Appointment appointment : appointments) {
                LocalDate dateStart = appointment.getDateStart().toLocalDate();
                if (dateStart.minusDays(1).isEqual(currentDate)) {
                    TwilioService.sendSms("+21625911145", "+13203587565", "Reminder Tobeeb");
                    // Send email to patient
                    String patientEmail = appointment.getPatient().getEmail();
                    SimpleMailMessage message = new SimpleMailMessage();
                    message.setTo(patientEmail);
                    message.setSubject("Appointment Reminder");
                    message.setText("Your appointment is tomorrow. Please make sure to arrive on time.");
                    javaMailSender.send(message);
                    System.out.println("Reminder email sent to " + patientEmail);
                }
            }
            return "Email reminders sent successfully.";
        } catch (Exception ex) {
            System.out.println("Error sending email reminders: " + ex.getMessage());
            return "Error sending email reminders: " + ex.getMessage();
        }
    }


    @GetMapping("/doctor/{id}")
    public ResponseEntity<List<AppointmentDTO>> getAppointmentForDoctor(@PathVariable Long id) {
        List<Appointment> appointments = appointmentService.getAllAppointments(id);
        System.out.println(appointments);
        List<AppointmentDTO> appointmentDTOS = new ArrayList<>();
        for (Appointment appointment : appointments) {
            AppointmentDTO appointmentDTO  = new AppointmentDTO();

            appointmentDTO.setIdAppointment((long) appointment.getIdAppointment());
            appointmentDTO.setDoctor(appointment.getDoctor().getUsername());
            appointmentDTO.setDoctorId(appointment.getDoctor().getId());
            appointmentDTO.setPatientId(appointment.getPatient().getId());
            appointmentDTO.setPatient(appointment.getPatient().getUsername());

            if (appointment.getConsultationFile() == null) {
                appointmentDTO.setConsultationFileId(0L);
            }
            else {
                appointmentDTO.setConsultationFileId(appointment.getConsultationFile().getIdFile());
            }
            appointmentDTO.setDateStart(appointment.getDateStart());
            appointmentDTOS.add(appointmentDTO);
        }
        return new ResponseEntity<>(appointmentDTOS, HttpStatus.OK);
    }


    @PostMapping("/addappointment")
    public Response addAppointment(@Validated @RequestBody Appointment appointment) {
        try {

            appointmentService.addAppointment(appointment);
            System.out.println("Received appointment object: " + appointment);
          SimpleMailMessage message = new SimpleMailMessage();
        //    TwilioService.sendSms("+21625911145", "+13203587565", "Reminder Tobeeb");

            message.setTo("rafed.benjeddou@esprit.tn");
         message.setSubject("Reservation");
           message.setText("Reservation added successfully");
           javaMailSender.send(message);
            return new Response("Reservation added successfully");
        } catch (IllegalArgumentException e) {
            return new Response("Reservation already taken");
        }
    }
    private static class Response {
        private String message;

        public Response(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
    @PutMapping("/updateappointment/{id}")
    public ResponseEntity<Appointment> updateAppointment(@PathVariable int id, @Validated @RequestBody Appointment appointment) {
        try {
            LocalDateTime currentDate = LocalDateTime.now();
            LocalDateTime reservationDate = appointment.getDateStart();

            if (reservationDate.isBefore(currentDate.plusDays(1))) {
                throw new IllegalArgumentException("Reservationns can only be updated one day before the current date");
            }

            Appointment existingAppointment = appointmentService.retrieveAppointment(id);
            if (existingAppointment == null) {
                throw new IllegalArgumentException("Appointment with id " + id + " not found");
            }

            appointment.setIdAppointment(id);
            Appointment updatedAppointment = appointmentService.updateAppointment(appointment);
            return ResponseEntity.ok(updatedAppointment);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/deleteappointment/{id}")
    public String deleteAppointment(@PathVariable("id") int id) {
        appointmentService.removeAppointmentById(id);
        return "Deleted Successfully";
    }


    @GetMapping("/appointment/{id}")

    public Appointment AffichercourstById(@PathVariable("id") int appointment)
    {
        return appointmentService.retrieveAppointment(appointment);
    }
    @GetMapping("/patient/{userId}/appointments")
    public List<Appointment> getAppointmentsByUser(@PathVariable Long userId) {
        User user = new User();
        user.setIdUser(userId);
        return appointmentService.findAppointmentsByUser(user);
    }
    @GetMapping("/doctor/{userId}/appointments")
    public List<Appointment> getAppointmentsByDoctor(@PathVariable Long userId) {
        User user = new User();
        user.setIdUser(userId);
        return appointmentService.findAppointmentsByDoctor(user);
    }
    @PostMapping("/charge")
    public ResponseEntity<String> charge (@RequestBody Map<String, Object> request) {
        try {
            String source = (String) request.get("source");
            int amount = (int) request.get("amount");
            Charge charge = paymentService.createCharge(amount, source);
            return ResponseEntity.ok("charge");
        } catch (StripeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}