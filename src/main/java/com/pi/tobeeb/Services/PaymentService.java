package com.pi.tobeeb.Services;
import com.stripe.model.Token;
import com.stripe.param.TokenCreateParams;
import com.stripe.param.TokenCreateParams.Builder;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Card;
import com.stripe.model.Charge;
import com.stripe.model.Customer;
import com.stripe.param.ChargeCreateParams;
import com.stripe.param.CustomerUpdateParams;
import com.stripe.param.issuing.CardCreateParams;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class PaymentService {
    private static final String STRIPE_SECRET_KEY = "sk_test_51MxfuEGPWonDGqDvQqpgUsd6tLBQBC9gcAhmpfhUeWutqDqDxOWvtM8VRaGPCsQIkMy2twnRQ2y4K1Ia8HKOuKmx00H3crhcF0";

    public Charge createCharge(int amount , String source) throws StripeException {
        Stripe.apiKey = STRIPE_SECRET_KEY;

        ChargeCreateParams params = ChargeCreateParams.builder()
                .setAmount((long)amount)
                .setCurrency("eur")
                .setDescription("description")
                .setSource(source)

                .build();
        Charge charge = Charge.create(params);
        return charge;
    }




}
