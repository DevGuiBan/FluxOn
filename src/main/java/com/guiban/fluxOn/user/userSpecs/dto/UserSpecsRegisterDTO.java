package com.guiban.fluxOn.user.userSpecs.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record UserSpecsRegisterDTO(UUID userId, UUID responsibilityId, String number, String cpf, String rg, BigDecimal salary, String paymentMethod, String paymentMethodDetails, String bank, String agency, String account) {
}
