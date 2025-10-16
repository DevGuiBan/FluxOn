package com.guiban.fluxOn.user.dto;

import org.springframework.lang.NonNull;

public record AuthenticationDTO(@NonNull String email,
                                @NonNull String password
) {}
