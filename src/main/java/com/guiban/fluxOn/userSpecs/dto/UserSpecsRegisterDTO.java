package com.guiban.fluxOn.userSpecs.dto;

import org.springframework.lang.NonNull;

import java.math.BigDecimal;
import java.util.UUID;

public record UserSpecsRegisterDTO(@NonNull UUID userId,
                                   @NonNull UUID responsibilityId,
                                   @NonNull String number,
                                   @NonNull String cpf,
                                   @NonNull String rg,
                                   @NonNull BigDecimal salary,
                                   String paymentMethod,
                                   String paymentMethodDetails,
                                   String bank,
                                   String agency,
                                   String account
) {}
