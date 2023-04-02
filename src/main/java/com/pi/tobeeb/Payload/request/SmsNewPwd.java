package com.pi.tobeeb.Payload.request;


import lombok.Data;

@Data

public class SmsNewPwd {
    private String phone;
    private String code;
    private String password;
}