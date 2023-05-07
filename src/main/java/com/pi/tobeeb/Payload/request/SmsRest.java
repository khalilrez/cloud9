package com.pi.tobeeb.Payload.request;

import lombok.Data;

@Data
public class SmsRest {
    private String phone;


	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}


}