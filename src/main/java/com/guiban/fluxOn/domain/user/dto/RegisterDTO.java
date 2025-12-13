package com.guiban.fluxOn.domain.user.dto;

import org.springframework.lang.NonNull;

public record RegisterDTO(@NonNull String name, @NonNull String email, @NonNull String password) {
}
