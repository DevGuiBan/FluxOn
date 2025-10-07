package com.guiban.fluxOn.user.dto;

import com.guiban.fluxOn.user.User;
import com.guiban.fluxOn.user.UserRole;
import com.guiban.fluxOn.userSpecs.PaymentMethodUser;
import com.guiban.fluxOn.userSpecs.UserSpecs;

import java.math.BigDecimal;

public record UserWithSpecsResponseDTO(
                                       String name,
                                       String email,
                                       UserRole role,
                                       boolean active,
                                       String responsibilityName,
                                       String number,
                                       String cpf,
                                       String rg,
                                       BigDecimal salary,
                                       PaymentMethodUser paymentMethod,
                                       String paymentMethodDetails,
                                       String bank,
                                       String agency,
                                       String account) {
    public UserWithSpecsResponseDTO(User user, UserSpecs userSpecs) {
        this(
                user.getName(),
                user.getEmail(),
                user.getRole(),
                user.isActive(),
                userSpecs.getResponsibility().getName(),
                userSpecs.getNumber(),
                userSpecs.getCpf(),
                userSpecs.getRg(),
                userSpecs.getSalary(),
                userSpecs.getPaymentMethod(),
                userSpecs.getPaymentMethodDetails(),
                userSpecs.getBank(),
                userSpecs.getAgency(),
                userSpecs.getAccount()
        );
    }
}
