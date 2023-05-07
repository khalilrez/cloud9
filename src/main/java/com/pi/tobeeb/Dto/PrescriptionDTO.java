package com.pi.tobeeb.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PrescriptionDTO {
    private Long prescriptionId;
    private String content;

    @DateTimeFormat(pattern = "yyyy-MM-JJ")
    private LocalDate date;
}
