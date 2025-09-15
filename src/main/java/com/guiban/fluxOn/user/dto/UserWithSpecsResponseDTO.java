package com.guiban.fluxOn.user.dto;

import com.guiban.fluxOn.responsibility.Responsibility;
import com.guiban.fluxOn.user.User;
import com.guiban.fluxOn.user.UserRole;
import com.guiban.fluxOn.user.userSpecs.PaymentMethodUser;
import com.guiban.fluxOn.user.userSpecs.UserSpecs;

import java.math.BigDecimal;
import java.util.UUID;

public record UserWithSpecsResponseDTO(UUID id, String name, String email, UserRole role, boolean active, Responsibility responsibilityId, String number, String cpf, String rg, BigDecimal salary, PaymentMethodUser paymentMethod, String paymentMethodDetails, String bank, String agency, String account) {
    public UserWithSpecsResponseDTO(User user, UserSpecs userSpecs) {
        this(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole(),
                user.isActive(),
                userSpecs.getResponsibility(),
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
