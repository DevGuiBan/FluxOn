package com.guiban.fluxOn.domain.workSchedule.dto;

import com.guiban.fluxOn.domain.user.User;
import com.guiban.fluxOn.domain.workSchedule.DayOfWeek;
import com.guiban.fluxOn.domain.workSchedule.Turn;
import com.guiban.fluxOn.domain.workSchedule.WorkSchedule;

import java.sql.Time;
import java.util.UUID;

public record WorkScheduleResponseDTO (UUID id, UUID userId, Turn turn, DayOfWeek dayOfWeek, Time startTime, Time endTime) {
    public WorkScheduleResponseDTO(WorkSchedule workSchedule , User user) {
        this(
                workSchedule.getId(),
                user.getId(),
                workSchedule.getTurn(),
                workSchedule.getDayOfWeek(),
                workSchedule.getStartTime(),
                workSchedule.getEndTime()
        );
    }
}
