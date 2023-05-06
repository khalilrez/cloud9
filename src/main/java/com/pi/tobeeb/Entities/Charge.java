package com.pi.tobeeb.Entities;

import com.stripe.model.PaymentSource;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Charge {
    private Long amount;
    private String currency;
    private String description;
    private String source;
}
