package com.guiban.fluxOn.workSchedule.dto;

import com.guiban.fluxOn.user.User;
import com.guiban.fluxOn.workSchedule.DayOfWeek;
import com.guiban.fluxOn.workSchedule.Turn;
import com.guiban.fluxOn.workSchedule.WorkSchedule;

import java.sql.Time;
import java.util.UUID;

public record WorkScheduleResponseDTO (UUID id, String name, Turn turn, DayOfWeek dayOfWeek, Time startTime, Time endTime) {
    public WorkScheduleResponseDTO (WorkSchedule workSchedule, User user) {
        this(
                workSchedule.getId(),
                user.getName(),
                workSchedule.getTurn(),
                workSchedule.getDayOfWeek(),
                workSchedule.getStartTime(),
                workSchedule.getEndTime()
        );
    }
}
