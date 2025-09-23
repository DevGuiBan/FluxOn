package com.guiban.fluxOn.workSchedule.dto;

import com.guiban.fluxOn.user.User;

import java.util.UUID;

public record WorkScheduleRegisterDTO (UUID userId, String dayOfWeek, String turn, String startTime, String endTime) {
}
