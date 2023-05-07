package com.pi.tobeeb.Services;

import com.pi.tobeeb.Entities.Appointment;
import com.pi.tobeeb.Utils.SmsConfig;
import com.twilio.http.TwilioRestClient;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService {
    @Autowired
    private SmsConfig smsService;

    public void sendNotification(Appointment appointment) {
        smsService.SendSMS(appointment.getPatient().getPhonenumber(),"Your Appointment #"+appointment.getIdAppointment()+" Starts at "+appointment.getDateStart());
        System.out.println("Email Sent");
    }
}