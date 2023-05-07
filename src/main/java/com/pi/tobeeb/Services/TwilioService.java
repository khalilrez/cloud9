package com.pi.tobeeb.Services;



import com.twilio.Twilio;
import com.twilio.http.TwilioRestClient;
import com.twilio.rest.api.v2010.account.Call;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class TwilioService {

    private final TwilioRestClient twilioClient;

    public TwilioService() {
        String ACCOUNT_SID = "AC60fad399992c657b33cad26fd9976d46";
        String AUTH_TOKEN = "2fc546e66190b47aab4ab348d6878951";
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        this.twilioClient = Twilio.getRestClient();
    }

    public static void sendSms(String to, String from, String messageBody) {
        Message.creator(
                        new PhoneNumber(to),
                        new PhoneNumber(from),
                        "Appointment Reminder \n" +
                                "Your appointment is tomorrow. Please make sure to arrive on time"
                                 )
                .create();

        System.out.println("Sent message: " + messageBody);
    }




}
