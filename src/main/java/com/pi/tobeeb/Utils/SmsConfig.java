package com.pi.tobeeb.Utils;

import com.twilio.Twilio;
import org.springframework.beans.factory.annotation.Autowired;
import com.twilio.type.PhoneNumber;
import com.twilio.rest.api.v2010.account.Message;
import org.springframework.stereotype.Service;

@Service

public class SmsConfig {
    @Autowired
    private TwilioConfig twilioConfig;

    //SMS
    public void SendSMS(String toNumber,String code){
        // Find your Account Sid and Token at twilio.com/user/account
        Twilio.init(twilioConfig.getAccount_sid(), twilioConfig.getAuth_token());

        Message message = Message.creator(new PhoneNumber("+216" + toNumber),
                new PhoneNumber(twilioConfig.getSender_number()),
                code).create();

        System.out.println(message.getSid());
    }
}
