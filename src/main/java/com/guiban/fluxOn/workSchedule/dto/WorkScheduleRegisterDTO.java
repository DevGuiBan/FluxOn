package com.guiban.fluxOn.workSchedule.dto;

import java.util.UUID;

public record WorkScheduleRegisterDTO (UUID userId, String dayOfWeek, String turn, String startTime, String endTime) {
}
