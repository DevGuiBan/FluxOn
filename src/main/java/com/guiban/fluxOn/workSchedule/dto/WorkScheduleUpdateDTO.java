package com.guiban.fluxOn.workSchedule.dto;

import java.sql.Time;
import java.util.UUID;

public record WorkScheduleUpdateDTO(UUID id, Time startTime, Time endTime, String dayOfWeek, String turn) {
}
