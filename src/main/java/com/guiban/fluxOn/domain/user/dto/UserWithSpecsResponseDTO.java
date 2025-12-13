package com.guiban.fluxOn.domain.user.dto;

import com.guiban.fluxOn.domain.user.User;
import com.guiban.fluxOn.domain.user.UserRole;
import com.guiban.fluxOn.domain.userSpecs.PaymentMethodUser;
import com.guiban.fluxOn.domain.userSpecs.UserSpecs;

import java.math.BigDecimal;
import java.util.UUID;

public record UserWithSpecsResponseDTO(
                                       UUID id,
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
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole(),
                user.isActive(),
                userSpecs != null && userSpecs.getResponsibility() != null ? userSpecs.getResponsibility().getName() : null,
                userSpecs != null ? userSpecs.getNumber() : null,
                userSpecs != null ? userSpecs.getCpf() : null,
                userSpecs != null ? userSpecs.getRg() : null,
                userSpecs != null ? userSpecs.getSalary() : null,
                userSpecs != null ? userSpecs.getPaymentMethod() : null,
                userSpecs != null ? userSpecs.getPaymentMethodDetails() : null,
                userSpecs != null ? userSpecs.getBank() : null,
                userSpecs != null ? userSpecs.getAgency() : null,
                userSpecs != null ? userSpecs.getAccount() : null
        );
    }
}
