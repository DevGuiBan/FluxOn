package com.guiban.fluxOn.workSchedule.dto;

import org.springframework.lang.NonNull;

import java.util.UUID;

public record WorkScheduleRegisterDTO (@NonNull UUID userId, @NonNull String dayOfWeek, @NonNull String turn, @NonNull String startTime, @NonNull String endTime) {
}
