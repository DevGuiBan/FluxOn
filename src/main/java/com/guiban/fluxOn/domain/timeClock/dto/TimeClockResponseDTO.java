package com.guiban.fluxOn.domain.timeClock.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.guiban.fluxOn.domain.timeClock.AttendanceStatus;
import com.guiban.fluxOn.domain.timeClock.TimeClock;
import com.guiban.fluxOn.domain.workSchedule.Turn;

import java.sql.Time;
import java.time.LocalDate;
import java.util.UUID;

public record TimeClockResponseDTO (UUID id,
                                    UUID userId,
                                    String userName,
                                    AttendanceStatus attendanceStatus,
                                    Turn turn,
                                    @JsonFormat(pattern = "yyyy-MM-dd") LocalDate date,
                                    @JsonFormat(pattern = "HH:mm:ss") Time clock,
                                    @JsonFormat(pattern = "HH:mm:ss") Time clockOut
                                    ) {
    
    public TimeClockResponseDTO(TimeClock timeClock) {
        this(
                timeClock.getId(),
                timeClock.getUser().getId(),
                timeClock.getUser().getName(),
                timeClock.getAttendanceStatus(),
                timeClock.getTurn(),
                timeClock.getDate(),
                timeClock.getClock(),
                timeClock.getClockOut()
        );
    }
}
