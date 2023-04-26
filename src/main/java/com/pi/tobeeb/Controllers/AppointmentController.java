package com.pi.tobeeb.Controllers;

import com.pi.tobeeb.Entities.Appointment;
import com.pi.tobeeb.Services.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/appointments")
public class AppointmentController {
    @Autowired
    private AppointmentService appointmentService;

    @GetMapping("/patient/{patientId}")
    ResponseEntity<?> getAppointmentsByPatient(@PathVariable int patientId) {
        
    }

    @GetMapping("/doctor/{doctorId}")
    ResponseEntity<?> getAppointmentsByDoctor(@PathVariable int doctorId){
        return null;
    }

    @GetMapping("/")
    ResponseEntity<?> addAppointment(@RequestBody Appointment appointment){
        return null;
    }

    @DeleteMapping("/{appointmentId}")
    ResponseEntity<?> deleteAppointment(@PathVariable int appointmentId){
        return null;
    }

    @PutMapping("/")
    ResponseEntity<?> updateAppointment(@RequestBody Appointment appointment){
        return null;
    }


}
