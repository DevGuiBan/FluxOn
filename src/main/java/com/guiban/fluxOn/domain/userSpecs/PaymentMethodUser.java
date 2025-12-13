package com.guiban.fluxOn.domain.userSpecs;

import lombok.Getter;

@Getter
public enum PaymentMethodUser {
    PIX("pix"),
    CASH("dinheiro"),
    BANK("banco");

    private final String paymentMethod;

    PaymentMethodUser(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

}
