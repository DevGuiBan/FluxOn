package com.guiban.fluxOn.timeClock.dto;

import java.sql.Time;
import java.util.UUID;

public record TimeClockRegisterDTO (UUID userId, Time clockIn, Time clockOut, String date, String attendanceStatus, String justification) {
}
