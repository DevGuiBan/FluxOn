package com.guiban.fluxOn.timeClock.dto;

import org.springframework.lang.NonNull;

import java.util.UUID;

public record TimeClockRegisterInDTO(@NonNull UUID userId,
                                     @NonNull String clock,
                                     @NonNull String date,
                                     @NonNull String attendanceStatus
) {}
