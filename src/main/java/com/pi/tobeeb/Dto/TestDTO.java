package com.pi.tobeeb.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Lob;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TestDTO {
    private Long idTest;
    private String testName;
    @Lob
    private byte[] image;
}
