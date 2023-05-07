package com.pi.tobeeb.Payload.appointments;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateAppointmentRequest {
    private String type;
    private LocalDate date;
    private Long doctorId;
    private Long patientId;
}
