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


@RestController
@RequestMapping("api/appointments")
@CrossOrigin(origins = "http://localhost:4200")
public class AppointmentController {
    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private AppointmentRepository appointmentRepository;


    @GetMapping("/api/appointments")
    public ResponseEntity<List<Appointment>> getAppointments(@RequestParam("date") String dateStr) {

        LocalDate date = LocalDate.parse(dateStr);

        List<Appointment> appointments = appointmentRepository.findAllByDateStart(date);

        return new ResponseEntity<>(appointments,HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Appointment> createAppointment(@RequestBody CreateAppointmentRequest createAppointmentRequest) {
        Appointment createdAppointment = appointmentService.createAppointment(createAppointmentRequest.getPatientId(),createAppointmentRequest.getDoctorId(),createAppointmentRequest);
        return new ResponseEntity<>(createdAppointment, HttpStatus.CREATED);
    }


    @PostMapping("/cancel/{id}")
    public ResponseEntity<Appointment> cancelAppointment(@PathVariable Long id ){
        Appointment appointment = appointmentService.cancelAppointment(id);
        return new ResponseEntity<>(appointment, HttpStatus.OK);
    }

    @PostMapping("/complete/{id}")
    public ResponseEntity<Appointment> completeAppointment(@PathVariable Long id ){
        Appointment appointment = appointmentService.completeAppointment(id);
        return new ResponseEntity<>(appointment, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Appointment> getAppointment(@PathVariable Long id) {
        Appointment appointment = appointmentService.getAppointment(id);
        return new ResponseEntity<>(appointment, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<AppointmentDTO>> getAllAppointments() {
        List<Appointment> appointments = appointmentService.getAllAppointments();
        System.out.println(appointments);
        List<AppointmentDTO> appointmentDTOS = new ArrayList<>();
        for (Appointment appointment : appointments) {
            AppointmentDTO appointmentDTO  = new AppointmentDTO();
            appointmentDTO.setIdAppointment(appointment.getIdAppointment());
            appointmentDTO.setDoctor(appointment.getDoctor().getUsername());
            appointmentDTO.setDoctorId(appointment.getDoctor().getId());
            appointmentDTO.setPatientId(appointment.getPatient().getId());
            appointmentDTO.setPatient(appointment.getPatient().getUsername());
            appointmentDTO.setConsultationFileId(appointment.getConsultationFile().getIdFile());
            appointmentDTO.setDateStart(appointment.getDateStart());
            appointmentDTOS.add(appointmentDTO);
        }
        return new ResponseEntity<>(appointmentDTOS, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Appointment> updateAppointment(@PathVariable Long id, @RequestBody Appointment appointment) {
        Appointment updatedAppointment = appointmentService.updateAppointment(id, appointment);
        return new ResponseEntity<>(updatedAppointment, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable Long id) {
        appointmentService.deleteAppointment(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}