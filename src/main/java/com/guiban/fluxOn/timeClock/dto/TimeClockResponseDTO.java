package com.guiban.fluxOn.timeClock.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.guiban.fluxOn.timeClock.AttendanceStatus;
import com.guiban.fluxOn.timeClock.TimeClock;

import java.sql.Time;
import java.util.Date;
import java.util.UUID;

public record TimeClockResponseDTO (UUID id,
                                    UUID userId,
                                    String userName,
                                    AttendanceStatus attendanceStatus,
                                    @JsonFormat(pattern = "dd-MM-yyyy") Date date,
                                    Time clockIn,
                                    Time clockOut,
                                    String justification) {
    
    public TimeClockResponseDTO(TimeClock timeClock) {
        this(
                timeClock.getId(),
                timeClock.getUser().getId(),
                timeClock.getUser().getName(),
                timeClock.getAttendanceStatus(),
                timeClock.getDate(),
                timeClock.getClockIn(),
                timeClock.getClockOut(),
                timeClock.getJustification()
        );
    }
}
