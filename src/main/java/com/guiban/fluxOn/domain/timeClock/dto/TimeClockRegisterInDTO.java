package com.guiban.fluxOn.domain.timeClock.dto;

import org.springframework.lang.NonNull;

import java.util.UUID;

public record TimeClockRegisterInDTO(@NonNull UUID userId,
                                     @NonNull String clock,
                                     @NonNull String date,
                                     @NonNull String attendanceStatus
) {}
