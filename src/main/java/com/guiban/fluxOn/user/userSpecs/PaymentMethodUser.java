package com.guiban.fluxOn.user.userSpecs;

public enum PaymentMethodUser {
    PIX("pix"),
    CASH("dinheiro"),
    BANK("banco");

    private String paymentMethod;

    PaymentMethodUser(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }
}
