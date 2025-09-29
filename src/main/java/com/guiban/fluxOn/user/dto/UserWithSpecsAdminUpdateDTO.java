package com.guiban.fluxOn.user.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record UserWithSpecsAdminUpdateDTO(
        String name,
        String email,
        String password,
        String role,
        UUID responsibilityId,
        BigDecimal salary,
        String number,
        String cpf,
        String rg,
        String paymentMethod,
        String paymentMethodDetails,
        String bank,
        String agency,
        String account

) {}
