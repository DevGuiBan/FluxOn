package com.guiban.fluxOn.user.dto;

import java.math.BigDecimal;

public record UserWithSpecsUserUpdateDTO(
        String name,
        String email,
        String password,
        String number,
        String cpf,
        String rg,
        String paymentMethod,
        String paymentMethodDetails,
        String bank,
        String agency,
        String account
) {}
