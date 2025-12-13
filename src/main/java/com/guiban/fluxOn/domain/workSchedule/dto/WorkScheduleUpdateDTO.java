package com.guiban.fluxOn.domain.workSchedule.dto;

import java.time.LocalTime;
import java.util.UUID;

public record WorkScheduleUpdateDTO(UUID id, LocalTime startTime, LocalTime endTime, String dayOfWeek, String turn) {
}
