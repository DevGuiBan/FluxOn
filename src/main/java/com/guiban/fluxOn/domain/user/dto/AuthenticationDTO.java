package com.guiban.fluxOn.domain.user.dto;

import org.springframework.lang.NonNull;

public record AuthenticationDTO(@NonNull String email,
                                @NonNull String password
) {}
