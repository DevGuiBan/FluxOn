package com.guiban.fluxOn.domain.workSchedule.dto;

import org.springframework.lang.NonNull;

import java.util.UUID;

public record WorkScheduleRegisterDTO (@NonNull UUID userId, @NonNull String dayOfWeek, @NonNull String turn, @NonNull String startTime, @NonNull String endTime) {
}
